package com.app.gui.patient;

/**
 * Created at : 4/27/2024
 */

import com.app.DBUtils.DBConnection;
import com.app.App;
import com.app.gui.familyhistory.FamilyHistoryForm;
import com.app.interview.FamilyHistoryInterview;
import com.app.other.Msg;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PatientListGUI extends JFrame {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    private DefaultTableModel tableModel;
    private JTable patientTable;
    private JButton add; // New button
    private JButton edit; // New button
    private JButton delete; // New button
    private JButton familyHistory; // New button

    public PatientListGUI() {
        super("Patient List Form");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 400);

        // Create table model
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Patient ID");
        tableModel.addColumn("Last Name");
        tableModel.addColumn("Previous Last Name");
        tableModel.addColumn("First Name");
        tableModel.addColumn("Home Address 1");
        tableModel.addColumn("Home City");
        tableModel.addColumn("Home State/Province/Region");
        tableModel.addColumn("Home Zip");
        tableModel.addColumn("Country");
        tableModel.addColumn("Citizenship");
        tableModel.addColumn("Mobile Phone");
        tableModel.addColumn("Emergency Phone");
        tableModel.addColumn("Email Address");
        tableModel.addColumn("SSN");
        tableModel.addColumn("DOB");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Ethnic Association");
        tableModel.addColumn("Marital Status");
        tableModel.addColumn("Current Primary HCP");
        tableModel.addColumn("Comments");
        tableModel.addColumn("Next of Kin");
        tableModel.addColumn("Next of Kin Relationship");

        // Create patient table
        patientTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(patientTable);
        scrollPane.setPreferredSize(new Dimension(780, 300));
        add(scrollPane, BorderLayout.CENTER);

        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);

        // View Patient Info button
        JPanel buttonPanel = new JPanel();
        add = new JButton("Add New");
        buttonPanel.add(add);
        add(buttonPanel, BorderLayout.SOUTH);

        edit = new JButton("Edit");
        buttonPanel.add(edit);
        add(buttonPanel, BorderLayout.SOUTH);

        delete = new JButton("Delete");
        buttonPanel.add(delete);
        add(buttonPanel, BorderLayout.SOUTH);

        familyHistory = new JButton("Family History");
        buttonPanel.add(familyHistory);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener for view Patient Info button
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPatient addPatientDialog = new AddPatient(PatientListGUI.this);
                addPatientDialog.setVisible(true);
                fetchPatientDataFromDatabase();
            }
        });
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = patientTable.getSelectedRows();


                if (selectedRows.length > 0) {
                    String patientID = patientTable.getValueAt(selectedRows[0],0).toString();
                    com.app.gui.patient.EditPatient editPatientDialog = new com.app.gui.patient.EditPatient(PatientListGUI.this,patientID );
                    editPatientDialog.setVisible(true);
                    fetchPatientDataFromDatabase();
                } else {
                    JOptionPane.showMessageDialog(PatientListGUI.this,
                            "Please select at least one patient.",
                            "No Patient Selected",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = patientTable.getSelectedRows();
                if (selectedRows.length > 0) {
                    removePatient();
                    dispose();
                    new App().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(PatientListGUI.this,
                            "Please select at least one patient.",
                            "No Patient Selected",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        familyHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FamilyHistoryForm().setVisible(true);
            }
        });

        // Fetch patient data from database
        fetchPatientDataFromDatabase();
    }

    // Method to fetch patient data from database
    private void fetchPatientDataFromDatabase() {
        try {
            // Establish database connection (replace with your database details)
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM patient where PatientID ="+ App.SELECTED_PATIENT_ID);

            // Clear existing table data
            tableModel.setRowCount(0);

            // Populate table with data from database
            while (rs.next()) {
                Object[] row = new Object[22];
                for (int i = 1; i <= 22; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void removePatient() {
        String sql = "DELETE FROM patient WHERE PatientID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // Set the value of the parameter in the PreparedStatement
            pstmt.setInt(1, App.SELECTED_PATIENT_ID);
            int rowsAffected = pstmt.executeUpdate();
            // Check if any rows were affected (patient deleted)
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, Msg.Delete, "Delete Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No patient found with ID " +  App.SELECTED_PATIENT_ID + ".", "Delete Failed", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PatientListGUI patientListGui = new PatientListGUI();
            patientListGui.setVisible(true);
            patientListGui.setExtendedState(JFrame.MAXIMIZED_BOTH);

        });
    }
}
