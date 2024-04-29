package com.app.DBUtils;

import com.app.App;

import java.sql.*;

public class PatientService {
    Connection conn = null;

    // Method to fetch patient data from database
    public ResultSet fetchPatientDataFromDatabase() {
        try {
            // Establish database connection (replace with your database details)
            conn = DBConnection.getConnection();
            CallableStatement stmt = conn.prepareCall("{CALL fetch_patient_demographics(?)}");
            stmt.setInt(1, App.SELECTED_PATIENT_ID);
             return  stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int deletePatient() throws SQLException {
        String sql = "DELETE FROM patient WHERE PatientID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        // Set the value of the parameter in the PreparedStatement
        pstmt.setInt(1, App.SELECTED_PATIENT_ID);
        return  pstmt.executeUpdate();
    }

}
