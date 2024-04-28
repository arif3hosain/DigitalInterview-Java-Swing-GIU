package com.interview.model.gui.allergy;

import DBUtils.DBConnection;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllergyHistoryForm extends JFrame {
    private JComboBox<String> allergenComboBox;
    private JTable allergyTable;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    String selectedAllergen;
    int patientId=1;

    public AllergyHistoryForm() {
        setTitle("Allergy History");
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
        // Initialize ComboBox
        allergenComboBox = new JComboBox<>();

        // Populate ComboBox with unique allergens for patient ID 1
        populateAllergenComboBox(1);

        // Add listener to ComboBox
        allergenComboBox.addActionListener(e -> {
            selectedAllergen = (String) allergenComboBox.getSelectedItem();
            populateAllergyTable(patientId, selectedAllergen);
        });

        // Initialize Table
        allergyTable = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Allergy ID", "Allergen", "Start Date", "End Date", "Description"}
        ));

        // Initialize Buttons
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        // Add listeners to buttons
        addButton.addActionListener(e -> showAddDialog());
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = allergyTable.getSelectedRows();
                if (selectedRows.length > 0) {
                    String allergyHistoryId = allergyTable.getValueAt(selectedRows[0],0).toString();
                    showEditDialog(Integer.parseInt(allergyHistoryId));
                    updateHistoryList(patientId, Integer.parseInt(allergyHistoryId));
                } else {
                    JOptionPane.showMessageDialog(AllergyHistoryForm.this,
                            "Please select at least one Item.",
                            "No Item Selected",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        deleteButton.addActionListener(e -> deleteSelectedAllergy());
    }

    private void populateAllergenComboBox(int patientID) {
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "SELECT DISTINCT Allergen FROM allergyhistory WHERE PatientID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, patientID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String allergen = resultSet.getString("Allergen");
                allergenComboBox.addItem(allergen);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void populateAllergyTable(int patientID, String allergen) {
        DefaultTableModel model = (DefaultTableModel) allergyTable.getModel();
        model.setRowCount(0); // Clear previous data

        try {
            Connection connection = DBConnection.getConnection();
            String sql = "SELECT AllergyID, Allergen, AllergyStartDate, AllergyEndDate, AllergyDescription FROM allergyhistory WHERE PatientID = ? AND Allergen = ?";
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

    private void updateHistoryList(int patientID,int historyId) {
        DefaultTableModel model = (DefaultTableModel) allergyTable.getModel();
        model.setRowCount(0); // Clear previous data

        try {
            Connection connection = DBConnection.getConnection();
            String sql = "SELECT AllergyID, Allergen, AllergyStartDate, AllergyEndDate, AllergyDescription FROM allergyhistory WHERE PatientID = ? AND AllergyID = ?";
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
        AddAllergyDialog dialog = new AddAllergyDialog(this);
        dialog.setVisible(true);
    }

    private void showEditDialog(int id) {
        EditAllergyDialog dialog = new EditAllergyDialog(this, id);
        dialog.setVisible(true);
    }

    private void deleteSelectedAllergy() {
        // Implement delete functionality
    }

    private void addComponentsToFrame() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(allergenComboBox, BorderLayout.NORTH);
        panel.add(new JScrollPane(allergyTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AllergyHistoryForm::new);
    }
}

class AddAllergyDialog extends JDialog {
    private JTextField allergenField;
    private JDateChooser allergyStartDate;
    private JDateChooser  allergyEndDate;
    private JTextField descriptionField;

    private JButton saveButton;
    private JButton cancelButton;

    public AddAllergyDialog(Frame parent) {
        super(parent, "Add Allergy", true);
        setSize(300, 200);

        // Initialize components
        initializeComponents();

        // Add components to the dialog
        addComponentsToDialog();

        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        allergenField = new JTextField(20);
        allergyStartDate = new JDateChooser();
        allergyEndDate = new JDateChooser();
        descriptionField = new JTextField(20);

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveAllergy());

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
    }

    private void addComponentsToDialog() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Allergen:"));
        panel.add(allergenField);
        panel.add(new JLabel("Start Date:"));
        panel.add(allergyStartDate);
        panel.add(new JLabel("End Date:"));
        panel.add(allergyEndDate);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel);
    }

    private void saveAllergy() {
        String allergen = allergenField.getText();
        java.util.Date startDate = allergyStartDate.getDate();
        java.util.Date endDate = allergyEndDate.getDate();

        // Check if required fields are empty
        if (allergen.isEmpty() || startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Allergen, Start Date, and End Date are required fields.");
            return; // Don't proceed with saving/updating
        }
        String description = descriptionField.getText();

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement;
                // Insert new allergy
                String sql = "INSERT INTO allergyhistory (PatientID, Allergen, AllergyStartDate, AllergyEndDate, AllergyDescription) VALUES (?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, 1); // Assuming patient ID is 1
                statement.setString(2, allergen);
                statement.setDate(3, new java.sql.Date(allergyStartDate.getDate().getTime()));
                statement.setDate(4, new java.sql.Date(allergyEndDate.getDate().getTime()));
                statement.setString(5, description);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Allergy saved/updated successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save/update allergy.");
            }
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

}
class EditAllergyDialog extends JDialog {
    private JTextField allergenField;
    private JDateChooser allergyStartDate;
    private JDateChooser allergyEndDate;
    private JTextField descriptionField;

    private JButton updateButton;
    private JButton cancelButton;

    private int allergyIDToUpdate;

    public EditAllergyDialog(Frame parent, int id) {
        super(parent, "Update History", true);
        setSize(300, 200);
        allergyIDToUpdate =id;
        // Initialize components
        initializeComponents();

        // Add components to the dialog
        addComponentsToDialog();
        loadAllergyData();
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        allergenField = new JTextField(20);
        allergyStartDate = new JDateChooser();
        allergyEndDate = new JDateChooser();
        descriptionField = new JTextField(20);

        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateAllergy());

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
    }

    private void addComponentsToDialog() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Allergen:"));
        panel.add(allergenField);
        panel.add(new JLabel("Start Date:"));
        panel.add(allergyStartDate);
        panel.add(new JLabel("End Date:"));
        panel.add(allergyEndDate);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(updateButton);
        panel.add(cancelButton);

        add(panel);
    }

    private void loadAllergyData() {
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "SELECT Allergen, AllergyStartDate, AllergyEndDate, AllergyDescription FROM allergyhistory WHERE AllergyID = ?";
            System.out.println(sql);
            System.out.println(allergyIDToUpdate);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, allergyIDToUpdate);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                allergenField.setText(resultSet.getString("Allergen"));

                java.util.Date startDate = new java.util.Date(resultSet.getDate("AllergyStartDate").getTime());
                java.util.Date endDate = new java.util.Date(resultSet.getDate("AllergyEndDate").getTime());
                allergyStartDate.setDate(startDate);
                allergyEndDate.setDate(endDate);
                descriptionField.setText(resultSet.getString("AllergyDescription"));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateAllergy() {
        String allergen = allergenField.getText();
        java.util.Date startDate = allergyStartDate.getDate();
        java.util.Date endDate = allergyEndDate.getDate();

        // Check if required fields are empty
        if (allergen.isEmpty() || startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Allergen, Start Date, and End Date are required fields.");
            return; // Don't proceed with saving/updating
        }
         String description = descriptionField.getText();

        try {
            Connection connection =  DBConnection.getConnection();
            String sql = "UPDATE allergyhistory SET Allergen = ?, AllergyStartDate = ?, AllergyEndDate = ?, AllergyDescription = ? WHERE AllergyID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, allergen);
            statement.setDate(2, new java.sql.Date(allergyStartDate.getDate().getTime()));
            statement.setDate(3, new java.sql.Date(allergyEndDate.getDate().getTime()));
            statement.setString(4, description);
            statement.setInt(5, allergyIDToUpdate);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Allergy updated successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update allergy.");
            }
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}