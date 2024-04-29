package com.app;


import com.app.DBUtils.DBConnection;
import com.app.gui.patient.PatientListGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;
import java.util.List;

public class App extends JFrame {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    private List<String> patientList;
    private JList<String> patientJList;
    private JTextField searchField;
    private JButton searchButton;
    private JButton viewPatientButton; // New button for viewing patient details
    public static int SELECTED_PATIENT_ID =2;

    public App() {
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
        viewPatientButton = new JButton("View Info");
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
                    SELECTED_PATIENT_ID = getPatientId(selectedPatient);
                    new PatientListGUI().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(App.this,
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
        try {
            // Establish database connection (replace with your database details)
            conn = DriverManager.getConnection(DBConnection.JDBC_URL, DBConnection.USERNAME, DBConnection.PASSWORD);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT CONCAT(PtFirstName, ' ', PtLastName) AS full_name FROM patient  ");

            // Clear existing patient list
            patientList.clear();

            while (rs.next()) {
                String fullName = rs.getString("full_name");
                patientList.add(fullName);
            }
            // Update JList with patient list
            patientJList.setListData(patientList.toArray(new String[0]));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Integer getPatientId(String patientName) {
        try {
            pstmt = conn.prepareStatement("SELECT * FROM patient WHERE CONCAT(PtFirstName, ' ', PtLastName) = ?");
            pstmt.setString(1, patientName);
            rs = pstmt.executeQuery();

            // Clear existing patient list
            patientList.clear();
            while (rs.next()) {
                return rs.getInt("PatientID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}