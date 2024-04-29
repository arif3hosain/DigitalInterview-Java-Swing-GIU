package com.interview.model.gui.patient;

/**
 * Created by: arif hosain
 * Mail: arif@innoweb.co
 * Created at : 4/27/2024
 */

import com.interview.DBUtils.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PatientListForm extends JFrame {
    private DefaultTableModel tableModel;
    private JTable patientTable;
    private JTextField searchField;
    private JButton searchButton;
    private JButton add; // New button
    private JButton edit; // New button
    private JButton delete; // New button

    public PatientListForm() {
        super("Patient List Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        searchField = new JTextField();
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchButton = new JButton("Search");
        searchPanel.add(searchButton, BorderLayout.EAST);
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

        // Add action listener for search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchPatientDataFromDatabase(); // Refresh patient table based on search term
            }
        });

        // Add action listener for view Patient Info button
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPatient addPatientDialog = new AddPatient(PatientListForm.this);
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
                    System.out.println(patientID);
                    EditPatient editPatientDialog = new EditPatient(PatientListForm.this,patientID );
                    editPatientDialog.setVisible(true);
                    fetchPatientDataFromDatabase();
                } else {
                    JOptionPane.showMessageDialog(PatientListForm.this,
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
                    // Open new GUI with full information of selected patient(s)
                    String[] selectedPatients = new String[selectedRows.length];
                    for (int i = 0; i < selectedRows.length; i++) {
                        selectedPatients[i] = (String) patientTable.getValueAt(selectedRows[i], 0);
                    }
                    new PatientInfoGUI (selectedPatients).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(PatientListForm.this,
                            "Please select at least one patient.",
                            "No Patient Selected",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Fetch patient data from database
        fetchPatientDataFromDatabase();
    }

    // Method to fetch patient data from database
    private void fetchPatientDataFromDatabase() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // Establish database connection (replace with your database details)
            conn = DriverManager.getConnection(DBConnection.JDBC_URL, DBConnection.USERNAME, DBConnection.PASSWORD);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM patient");

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
        } finally {
            // Close JDBC resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PatientListForm patientListForm = new PatientListForm();
            patientListForm.setVisible(true);
            patientListForm.setExtendedState(JFrame.MAXIMIZED_BOTH);

        });
    }
}
