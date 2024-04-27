package com.interview.model.gui;


import DBUtils.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientSelectionGUI extends JFrame {
    private List<String> patientList;
    private JList<String> patientJList;
    private JTextField searchField;
    private JButton searchButton;
    private JButton viewPatientButton; // New button for viewing patient details

    public PatientSelectionGUI() {
        super("Patient Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Initialize patient list
        patientList = new ArrayList<>();

        // Create patient selection panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchButton = new JButton("Search");
        searchPanel.add(searchButton, BorderLayout.EAST);
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Patient list panel
        JPanel listPanel = new JPanel(new BorderLayout());
        patientJList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(patientJList);
        listPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(listPanel, BorderLayout.CENTER);

        // View Patient button
        viewPatientButton = new JButton("View Patient");
        mainPanel.add(viewPatientButton, BorderLayout.SOUTH);

        add(mainPanel);

        // Add action listener for search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().trim().toLowerCase();
                if (!searchTerm.isEmpty()) {
                    // Filter patient list based on search term
                    List<String> filteredList = new ArrayList<>();
                    for (String patient : patientList) {
                        if (patient.toLowerCase().contains(searchTerm)) {
                            filteredList.add(patient);
                        }
                    }
                    // Update JList with filtered patient list
                    patientJList.setListData(filteredList.toArray(new String[0]));
                } else {
                    // Reset patient list if search term is empty
                    patientJList.setListData(patientList.toArray(new String[0]));
                }
            }
        });

        // Add action listener for view Patient button
        viewPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPatient = patientJList.getSelectedValue();
                if (selectedPatient != null) {
                    // Open PatientDetailsGUI with selected patient's details
                    new PatientDetailsGUI(selectedPatient).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(PatientSelectionGUI.this,
                            "Please select a patient.",
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
            rs = stmt.executeQuery("SELECT CONCAT(PtFirstName, ' ', PtLastName) AS full_name FROM patient");

            // Clear existing patient list
            patientList.clear();

            // Populate patient list with data from database
            while (rs.next()) {
                String fullName = rs.getString("full_name");
                patientList.add(fullName);
            }

            // Update JList with patient list
            patientJList.setListData(patientList.toArray(new String[0]));

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
            PatientSelectionGUI patientSelectionGUI = new PatientSelectionGUI();
            patientSelectionGUI.setVisible(true);
        });
    }
}

class PatientDetailsGUI extends JFrame {
    public PatientDetailsGUI(String patientName) {
        super("Patient Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);

        // Initialize components
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JLabel nameLabel = new JLabel("Name: " + patientName);
        panel.add(nameLabel);

        // Fetch patient details from database
        fetchPatientDetailsFromDatabase(patientName, panel);

        add(panel);
    }

    private void fetchPatientDetailsFromDatabase(String patientName, JPanel panel) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // Establish database connection (replace with your database details)
            conn = DriverManager.getConnection(DBConnection.JDBC_URL, DBConnection.USERNAME, DBConnection.PASSWORD);
            pstmt = conn.prepareStatement("SELECT * FROM patient WHERE CONCAT(PtFirstName, ' ', PtLastName) = ?");
            pstmt.setString(1, patientName);
            rs = pstmt.executeQuery();

            // Display patient details
            if (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String value = rs.getString(columnName);
                    JLabel label = new JLabel(columnName + ": " + value);
                    panel.add(label);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close JDBC resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
