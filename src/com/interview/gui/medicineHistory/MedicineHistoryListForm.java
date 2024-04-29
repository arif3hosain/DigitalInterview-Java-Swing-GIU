package com.interview.gui.medicineHistory;


import com.interview.DBUtils.DBConnection;
import com.interview.model.gui.medicineHistory.AddMedicalHistoryDialog;
import com.interview.model.gui.medicineHistory.UpdateMedicalHistoryDialog;
import com.interview.model.gui.patient.PatientInfoGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MedicineHistoryListForm extends JFrame {
    private DefaultTableModel tableModel;
    private JTable patientTable;
    private JTextField searchField;
    private JButton searchButton;
    private JButton add; // New button
    private JButton edit; // New button
    private JButton delete; // New button
    private String patientId = "2";
    String[] columnNames = {"GeneralMedicalHistoryID", "PatientID", "Tobacco", "TobaccoQuantity",
            "Tobaccoduration", "Alcohol", "AlcoholQuantity", "Alcoholduration",
            "Drug", "DrugType", "Drugduration", "BloodType", "Rh", "deleted"};

    public MedicineHistoryListForm() {
        super("Medical History Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (String columnName : columnNames) {
            tableModel.addColumn(columnName);
        }
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
                com.interview.model.gui.medicineHistory.AddMedicalHistoryDialog dialog = new AddMedicalHistoryDialog(MedicineHistoryListForm.this, patientId);
                dialog.setVisible(true);
                fetchPatientDataFromDatabase();
            }
        });
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = patientTable.getSelectedRows();
                if (selectedRows.length > 0) {
                    String id = patientTable.getValueAt(selectedRows[0],0).toString();
                    com.interview.model.gui.medicineHistory.UpdateMedicalHistoryDialog dialog = new UpdateMedicalHistoryDialog(MedicineHistoryListForm.this, id);
                    dialog.setVisible(true);
                    fetchPatientDataFromDatabase();
                } else {
                    JOptionPane.showMessageDialog(MedicineHistoryListForm.this,
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
                    new PatientInfoGUI(selectedPatients).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(MedicineHistoryListForm.this,
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
            rs = stmt.executeQuery("SELECT * FROM medicalhistory WHERE PatientID = " + 1);

            // Clear existing table data
            tableModel.setRowCount(0);

            // Populate table with data from database
            // Add rows to the table model
            while (rs.next()) {
                Object[] row = new Object[columnNames.length];
                for (int i = 0; i < columnNames.length; i++) {
                    row[i] = rs.getObject(columnNames[i]);
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
            MedicineHistoryListForm patientListForm = new MedicineHistoryListForm();
            patientListForm.setVisible(true);
            patientListForm.setExtendedState(JFrame.MAXIMIZED_BOTH);

        });
    }
}
