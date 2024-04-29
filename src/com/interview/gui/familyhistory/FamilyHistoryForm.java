package com.interview.gui.familyhistory;

import com.interview.DBUtils.DBConnection;
import com.interview.other.Msg;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FamilyHistoryForm extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    int patientId = 1;

    public FamilyHistoryForm() {
        setTitle("Family History Form");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        initializeComponents();

        // Add components to the frame
        addComponentsToFrame();

        // Set frame visibility
        setVisible(true);
    }

    private void initializeComponents() {
        // Initialize Table

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("FamilyID");
        model.addColumn("Name");
        model.addColumn("Relation");
        model.addColumn("Alive");
        model.addColumn("Lives with Patient");
        model.addColumn("Major Disorder");
        model.addColumn("Specific Type Disorder");
        model.addColumn("Disorder HRF");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        // Initialize Buttons
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        fetchDataFromDatabase(patientId);

        // Add listeners to buttons
        addButton.addActionListener(e -> showAddDialog());
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length > 0) {
                    String FamilyHistoryId = table.getValueAt(selectedRows[0], 0).toString();
                    showEditDialog(Integer.parseInt(FamilyHistoryId));
                    fetchDataFromDatabase(patientId);
                } else {
                    JOptionPane.showMessageDialog(FamilyHistoryForm.this,
                            "Please select at least one Item.",
                            "No Item Selected",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        deleteButton.addActionListener(e -> deleteSelectedAllergy());
    }

    private void fetchDataFromDatabase(int patientID) {
        try {
            // Connect to your database (replace 'url', 'user', and 'password' with your actual database credentials)
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM familyhistory WHERE PatientID = ?");
            stmt.setInt(1, patientID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("FamilyID"),
                        rs.getString("Name"),
                        rs.getString("Relation"),
                        rs.getBoolean("Alive"),
                        rs.getBoolean("Lives_with_patient"),
                        rs.getString("MajorDisorder"),
                        rs.getString("SpecificTypeDisorder"),
                        rs.getBoolean("DisorderHRF")
                };
                model.addRow(row);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populatetable(int patientID, String allergen) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear previous data

        try {
            Connection connection = DBConnection.getConnection();
            String sql = "SELECT AllergyID, Allergen, AllergyStartDate, AllergyEndDate, AllergyDescription FROM FamilyHistory WHERE PatientID = ? AND Allergen = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, patientID);
            statement.setString(2, allergen);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int allergyID = resultSet.getInt("AllergyID");
                String startDate = resultSet.getString("AllergyStartDate");
                String endDate = resultSet.getString("AllergyEndDate");
                String description = resultSet.getString("AllergyDescription");
                model.addRow(new Object[]{allergyID, allergen, startDate, endDate, description});
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void updateHistoryList(int patientID, int historyId) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear previous data

        try {
            Connection connection = DBConnection.getConnection();
            String sql = "SELECT * FROM familyhistory WHERE PatientID = ? AND FamilyID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, patientID);
            statement.setInt(2, historyId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int allergyID = resultSet.getInt("AllergyID");
                String startDate = resultSet.getString("AllergyStartDate");
                String endDate = resultSet.getString("AllergyEndDate");
                String description = resultSet.getString("AllergyDescription");
                String Allergen = resultSet.getString("Allergen");
                model.addRow(new Object[]{allergyID, Allergen, startDate, endDate, description});
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void showAddDialog() {
        AddFamilyHistoryDialog dialog = new AddFamilyHistoryDialog(this);
        dialog.setVisible(true);
        fetchDataFromDatabase(patientId);
    }

    private void showEditDialog(int id) {
        UpdateFamilyHistoryDialog dialog = new UpdateFamilyHistoryDialog(this, id);
        dialog.setVisible(true);
    }

    private void deleteSelectedAllergy() {
        // Implement delete functionality
    }

    private void addComponentsToFrame() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FamilyHistoryForm::new);
    }
}

class AddFamilyHistoryDialog extends JDialog {

    private JTextField nameField, relationField, majorDisorderField, specificTypeDisorderField;
    private JCheckBox aliveCheckBox, livesWithPatientCheckBox, disorderHRFCheckBox;

    public AddFamilyHistoryDialog(JFrame parent) {
        super(parent, "Add Family History", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx++;
        nameField = new JTextField(20);
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Relation:"), gbc);
        gbc.gridx++;
        relationField = new JTextField(20);
        panel.add(relationField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Major Disorder:"), gbc);
        gbc.gridx++;
        majorDisorderField = new JTextField(20);
        panel.add(majorDisorderField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Specific Type Disorder:"), gbc);
        gbc.gridx++;
        specificTypeDisorderField = new JTextField(20);
        panel.add(specificTypeDisorderField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Alive:"), gbc);
        gbc.gridx++;
        aliveCheckBox = new JCheckBox();
        panel.add(aliveCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Lives with Patient:"), gbc);
        gbc.gridx++;
        livesWithPatientCheckBox = new JCheckBox();
        panel.add(livesWithPatientCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Disorder HRF:"), gbc);
        gbc.gridx++;
        disorderHRFCheckBox = new JCheckBox();
        panel.add(disorderHRFCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    saveFamilyHistory();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(AddFamilyHistoryDialog.this,
                            "Name and Relation fields are required.",
                            "com.interview.Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(saveButton, gbc);

        gbc.gridx++;
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cancelButton, gbc);

        add(panel);
    }
    private boolean validateFields() {
        String name = nameField.getText().trim();
        String relation = relationField.getText().trim();
        return !name.isEmpty() && !relation.isEmpty();
    }
    private void saveFamilyHistory() {
        try {
            // Connect to your database (replace 'url', 'user', and 'password' with your actual database credentials)
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO familyhistory (PatientID, Name, Relation, Alive, Lives_with_patient, MajorDisorder, SpecificTypeDisorder, DisorderHRF) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            // Set parameters for the prepared statement
            stmt.setInt(1, 1); // Assuming PatientID is 1
            stmt.setString(2, nameField.getText());
            stmt.setString(3, relationField.getText());
            stmt.setBoolean(4, aliveCheckBox.isSelected());
            stmt.setBoolean(5, livesWithPatientCheckBox.isSelected());
            stmt.setString(6, majorDisorderField.getText());
            stmt.setString(7, specificTypeDisorderField.getText());
            stmt.setBoolean(8, disorderHRFCheckBox.isSelected());
            int affectedRows =  stmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, Msg.Save);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, Msg.Save_Failure);
            }


            // Close resources
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle any potential errors here
        }
    }
}

class UpdateFamilyHistoryDialog extends JDialog {

    private JTextField nameField, relationField, majorDisorderField, specificTypeDisorderField;
    private JCheckBox aliveCheckBox, livesWithPatientCheckBox, disorderHRFCheckBox;
    private int familyID;

    public UpdateFamilyHistoryDialog(JFrame parent,  int id) {
        super(parent, "Update Family History", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        familyID = id;
        try {


        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM familyhistory WHERE PatientID = ? AND FamilyID = ?");
        stmt.setInt(1, 1);
        stmt.setInt(2, 6);
        ResultSet rs = stmt.executeQuery();


        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

            if (rs.next()) {
                panel.add(new JLabel("Name:"), gbc);
                gbc.gridx++;
                nameField = new JTextField(rs.getString("Name"), 20);
                panel.add(nameField, gbc);

                gbc.gridx = 0;
                gbc.gridy++;
                panel.add(new JLabel("Relation:"), gbc);
                gbc.gridx++;
                relationField = new JTextField(rs.getString("Relation"), 20);
                panel.add(relationField, gbc);

                gbc.gridx = 0;
                gbc.gridy++;
                panel.add(new JLabel("Major Disorder:"), gbc);
                gbc.gridx++;
                majorDisorderField = new JTextField(rs.getString("MajorDisorder"), 20);
                panel.add(majorDisorderField, gbc);

                gbc.gridx = 0;
                gbc.gridy++;
                panel.add(new JLabel("Specific Type Disorder:"), gbc);
                gbc.gridx++;
                specificTypeDisorderField = new JTextField(rs.getString("SpecificTypeDisorder"), 20);
                panel.add(specificTypeDisorderField, gbc);

                gbc.gridx = 0;
                gbc.gridy++;
                panel.add(new JLabel("Alive:"), gbc);
                gbc.gridx++;
                aliveCheckBox = new JCheckBox();
                aliveCheckBox.setSelected(rs.getBoolean("Alive"));
                panel.add(aliveCheckBox, gbc);

                gbc.gridx = 0;
                gbc.gridy++;
                panel.add(new JLabel("Lives with Patient:"), gbc);
                gbc.gridx++;
                livesWithPatientCheckBox = new JCheckBox();
                livesWithPatientCheckBox.setSelected(rs.getBoolean("Lives_with_patient"));
                panel.add(livesWithPatientCheckBox, gbc);

                gbc.gridx = 0;
                gbc.gridy++;
                panel.add(new JLabel("Disorder HRF:"), gbc);
                gbc.gridx++;
                disorderHRFCheckBox = new JCheckBox();
                disorderHRFCheckBox.setSelected(rs.getBoolean("DisorderHRF"));
                panel.add(disorderHRFCheckBox, gbc);

            }

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;



        JButton saveButton = new JButton("Update");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    updateFamilyHistory();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(UpdateFamilyHistoryDialog.this,
                            "Name and Relation fields are required.",
                            "com.interview.Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(saveButton, gbc);

        gbc.gridx++;
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cancelButton, gbc);

        add(panel);

        }catch (SQLException e){
e.printStackTrace();
        }
    }
    private boolean validateFields() {
        String name = nameField.getText().trim();
        String relation = relationField.getText().trim();
        return !name.isEmpty() && !relation.isEmpty();
    }
    private void updateFamilyHistory() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE familyhistory SET Name=?, Relation=?, Alive=?, Lives_with_patient=?, MajorDisorder=?, SpecificTypeDisorder=?, DisorderHRF=? WHERE FamilyID=?");

            // Set parameters for the prepared statement
            stmt.setString(1, nameField.getText());
            stmt.setString(2, relationField.getText());
            stmt.setBoolean(3, aliveCheckBox.isSelected());
            stmt.setBoolean(4, livesWithPatientCheckBox.isSelected());
            stmt.setString(5, majorDisorderField.getText());
            stmt.setString(6, specificTypeDisorderField.getText());
            stmt.setBoolean(7, disorderHRFCheckBox.isSelected());
            stmt.setInt(8, familyID);

            // Execute the statement
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, Msg.Update);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, Msg.Update_Failure);
            }
            // Close resources
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle any potential errors here
        }
    }
}