package com.interview.model.gui.patient;

import DBUtils.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientInfoGUI extends JFrame {
    private DefaultTableModel tableModel;

    public PatientInfoGUI(String[] patientIds) {
        super("Patient Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);

        // Create table model
        tableModel = new DefaultTableModel();
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
        JTable patientTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(patientTable);
        scrollPane.setPreferredSize(new Dimension(780, 300));
        add(scrollPane, BorderLayout.CENTER);

        // Fetch patient data from database and add to table
        fetchPatientDataFromDatabase(patientIds);
    }

    // Method to fetch patient data from database
    private void fetchPatientDataFromDatabase(String[] patientIds) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // Establish database connection (replace with your database details)
            conn = DriverManager.getConnection(DBConnection.JDBC_URL, DBConnection.USERNAME, DBConnection.PASSWORD);

            // Prepare SQL query to fetch patient information for selected patient IDs
            StringBuilder query = new StringBuilder("SELECT * FROM patient WHERE PatientID IN (");
            for (int i = 0; i < patientIds.length; i++) {
                if (i > 0) {
                    query.append(", ");
                }
                query.append("?");
            }
            query.append(")");

            // Prepare statement
            pstmt = conn.prepareStatement(query.toString());

            // Set patient IDs as parameters
            for (int i = 0; i < patientIds.length; i++) {
                pstmt.setInt(i + 1, Integer.parseInt(patientIds[i]));
            }

            // Execute query
            rs = pstmt.executeQuery();

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
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
