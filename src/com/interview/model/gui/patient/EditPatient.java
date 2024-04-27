package com.interview.model.gui.patient;


import DBUtils.DBConnection;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class EditPatient extends JDialog {
    private JTextField lastNameField;
    private JTextField prevLastNameField;
    private JTextField firstNameField;
    private JTextField homeAddressField;
    private JTextField homeCityField;
    private JTextField homeStateField;
    private JTextField homeZipField;
    private JTextField countryField;
    private JTextField citizenshipField;
    private JTextField mobilePhoneField;
    private JTextField emergencyPhoneField;
    private JTextField emailField;
    private JTextField ssnField;
    private JDateChooser dobPicker; // Date picker for DOB
    private JComboBox<String> genderComboBox; // Combo box for gender
    private JTextField ethnicField;
    private JComboBox<String> maritalStatusField;
    private JTextField primaryHCPField;
    private JTextField commentsField;
    private JTextField nextOfKinField;
    private JTextField nextOfKinRelationshipField;
    private String patientIDGlobal;

    public EditPatient(JFrame parent, String patientID) {

        super(parent, "Update Patient", true);
        setSize(400, 600);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        add(panel);

        panel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        panel.add(lastNameField);

        panel.add(new JLabel("Previous Last Name:"));
        prevLastNameField = new JTextField();
        panel.add(prevLastNameField);

        panel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        panel.add(firstNameField);

        panel.add(new JLabel("Home Address:"));
        homeAddressField = new JTextField();
        panel.add(homeAddressField);

        panel.add(new JLabel("Home City:"));
        homeCityField = new JTextField();
        panel.add(homeCityField);

        panel.add(new JLabel("Home State/Province/Region:"));
        homeStateField = new JTextField();
        panel.add(homeStateField);

        panel.add(new JLabel("Home Zip:"));
        homeZipField = new JTextField();
        panel.add(homeZipField);

        panel.add(new JLabel("Country:"));
        countryField = new JTextField();
        panel.add(countryField);

        panel.add(new JLabel("Citizenship:"));
        citizenshipField = new JTextField();
        panel.add(citizenshipField);

        panel.add(new JLabel("Mobile Phone:"));
        mobilePhoneField = new JTextField();
        panel.add(mobilePhoneField);

        panel.add(new JLabel("Emergency Phone:"));
        emergencyPhoneField = new JTextField();
        panel.add(emergencyPhoneField);

        panel.add(new JLabel("Email Address:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("SSN:"));
        ssnField = new JTextField();
        panel.add(ssnField);

        panel.add(new JLabel("Date of Birth:"));
        dobPicker = new JDateChooser(); // Date picker for DOB
        panel.add(dobPicker);

        panel.add(new JLabel("Gender:"));
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"}); // Combo box for gender
        panel.add(genderComboBox);

        JButton updateButton = new JButton("Update");
        panel.add(updateButton);

        panel.add(new JLabel("Ethnic Association:"));
        ethnicField = new JTextField();
        panel.add(ethnicField);

        panel.add(new JLabel("Marital Status:"));
        maritalStatusField = new JComboBox<>(new String[]{"Married", "Single"}); // Combo box for gender
        panel.add(maritalStatusField);

        panel.add(new JLabel("Current Primary HCP:"));
        primaryHCPField = new JTextField();
        panel.add(primaryHCPField);

        panel.add(new JLabel("Comments:"));
        commentsField = new JTextField();
        panel.add(commentsField);

        panel.add(new JLabel("Next of Kin:"));
        nextOfKinField = new JTextField();
        panel.add(nextOfKinField);

        panel.add(new JLabel("Next of Kin Relationship:"));
        nextOfKinRelationshipField = new JTextField();
        panel.add(nextOfKinRelationshipField);
        patientIDGlobal = patientID;
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePatientRecord();
            }
        });
        panel.add(updateButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cancelButton);
        fetchPatientDataFromDatabase();
       
    }
    private void fetchPatientDataFromDatabase() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // Establish database connection (replace with your database details)
            conn = DriverManager.getConnection(DBConnection.JDBC_URL, DBConnection.USERNAME, DBConnection.PASSWORD);
            String query = "SELECT * FROM patient WHERE PatientID = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, patientIDGlobal);
            rs = pstmt.executeQuery();

            // Check if result set is not empty
            if (rs.next()) {
                // Populate fields with fetched data
                lastNameField.setText(rs.getString("PtLastName"));
                prevLastNameField.setText(rs.getString("PtPreviousLastName"));
                firstNameField.setText(rs.getString("PtFirstName"));
                homeAddressField.setText(rs.getString("HomeAddress1"));
                homeCityField.setText(rs.getString("HomeCity"));
                homeStateField.setText(rs.getString("HomeState_Province_Region"));
                homeZipField.setText(rs.getString("HomeZip"));
                countryField.setText(rs.getString("Country"));
                citizenshipField.setText(rs.getString("Citizenship"));
                mobilePhoneField.setText(rs.getString("PtMobilePhone"));
                emergencyPhoneField.setText(rs.getString("EmergencyPhoneNumber"));
                emailField.setText(rs.getString("EmailAddress"));
                ssnField.setText(rs.getString("PtSS"));
                // For DOB, you may need to convert the date format based on your application requirements
                Date dob = rs.getDate("DOB");
                if (dob != null) {
                    dobPicker.setDate(dob);
                }
                // For gender, set selected item in combo box
                String gender = rs.getString("Gender");
                if (gender != null) {
                    genderComboBox.setSelectedItem(gender);
                }
                ethnicField.setText(rs.getString("EthnicAssociation"));
                // For marital status, set selected item in combo box
                String maritalStatus = rs.getString("MaritalStatus");
                if (maritalStatus != null) {
                    maritalStatusField.setSelectedItem(maritalStatus);
                }
                primaryHCPField.setText(rs.getString("CurrentPrimaryHCP"));
                commentsField.setText(rs.getString("Comments"));
                nextOfKinField.setText(rs.getString("NextOfKin"));
                nextOfKinRelationshipField.setText(rs.getString("NextOfKinRelationshipToPatient"));
            } else {
                JOptionPane.showMessageDialog(this, "Patient with ID " + patientIDGlobal + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                dispose();
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
    private void updatePatientRecord() {
        if (lastNameField.getText().isEmpty() || firstNameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "First Name & Last Name are required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit method if name is null
        }

        // Get the values from the text fields
        String lastName = lastNameField.getText();
        String prevLastName = prevLastNameField.getText();
        String firstName = firstNameField.getText();
        String homeAddress = homeAddressField.getText();
        String homeCity = homeCityField.getText();
        String homeState = homeStateField.getText();
        String homeZip = homeZipField.getText();
        String country = countryField.getText();
        String citizenship = citizenshipField.getText();
        String mobilePhone = mobilePhoneField.getText();
        String emergencyPhone = emergencyPhoneField.getText();
        String email = emailField.getText();
        String ssn = ssnField.getText();
        // Convert dobPicker date to a string in the desired format
//        Date dob = dobPicker.getDate();
        Date dobUtil = new java.sql.Date(dobPicker.getDate().getTime());
        // Format the date to match the database date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dobStr = (dobUtil != null) ? dateFormat.format(dobUtil) : null;
        // Get the selected gender from the combo box
        String gender = (String) genderComboBox.getSelectedItem();
        String ethnic = ethnicField.getText();
        // Get the selected marital status from the combo box
        String maritalStatus = (String) maritalStatusField.getSelectedItem();
        String primaryHCP = primaryHCPField.getText();
        String comments = commentsField.getText();
        String nextOfKin = nextOfKinField.getText();
        String nextOfKinRelationship = nextOfKinRelationshipField.getText();

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Establish database connection (replace with your database details)
            conn = DriverManager.getConnection(DBConnection.JDBC_URL, DBConnection.USERNAME, DBConnection.PASSWORD);

            // Construct the SQL query to update the patient record
            String query = "UPDATE patient SET PtLastName=?, PtPreviousLastName=?, PtFirstName=?, " +
                    "HomeAddress1=?, HomeCity=?, HomeState_Province_Region=?, HomeZip=?, " +
                    "Country=?, Citizenship=?, PtMobilePhone=?, EmergencyPhoneNumber=?, " +
                    "EmailAddress=?, PtSS=?, DOB=?, Gender=?, EthnicAssociation=?, " +
                    "MaritalStatus=?, CurrentPrimaryHCP=?, Comments=?, NextOfKin=?, " +
                    "NextOfKinRelationshipToPatient=? WHERE PatientID=?";

            // Create a prepared statement with the SQL query
            pstmt = conn.prepareStatement(query);
            // Set values for parameters in the prepared statement
            pstmt.setString(1, lastName);
            pstmt.setString(2, prevLastName);
            pstmt.setString(3, firstName);
            pstmt.setString(4, homeAddress);
            pstmt.setString(5, homeCity);
            pstmt.setString(6, homeState);
            pstmt.setString(7, homeZip);
            pstmt.setString(8, country);
            pstmt.setString(9, citizenship);
            pstmt.setString(10, mobilePhone);
            pstmt.setString(11, emergencyPhone);
            pstmt.setString(12, email);
            pstmt.setString(13, ssn);
            pstmt.setString(14, dobStr);
            pstmt.setString(15, gender);
            pstmt.setString(16, ethnic);
            pstmt.setString(17, maritalStatus);
            pstmt.setString(18, primaryHCP);
            pstmt.setString(19, comments);
            pstmt.setString(20, nextOfKin);
            pstmt.setString(21, nextOfKinRelationship);
            pstmt.setString(22, patientIDGlobal);

            // Execute the update query
            int rowsAffected = pstmt.executeUpdate();

            // Check if the update was successful
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Patient record updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close the dialog after successful update
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update patient record.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while updating patient record.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close JDBC resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void savePatientRecord() {
        if (lastNameField.getText().isEmpty() || firstNameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "First Name & Last Name are required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit method if name is null
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Establish database connection (replace with your database details)
            conn = DriverManager.getConnection(DBConnection.JDBC_URL, DBConnection.USERNAME, DBConnection.PASSWORD);

            // Prepare SQL insert statement
            String sql = "INSERT INTO patient (PtLastName, PtPreviousLastName, PtFirstName, " +
                    "HomeAddress1, HomeCity, HomeState_Province_Region, HomeZip, " +
                    "Country, Citizenship, PtMobilePhone, EmergencyPhoneNumber, " +
                    "EmailAddress, PtSS, DOB, Gender, EthnicAssociation, " +
                    "MaritalStatus, CurrentPrimaryHCP, Comments, NextOfKin, " +
                    "NextOfKinRelationshipToPatient) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            // Set parameter values
            pstmt.setString(1, lastNameField.getText());
            pstmt.setString(2, prevLastNameField.getText());
            pstmt.setString(3, firstNameField.getText());
            pstmt.setString(4, homeAddressField.getText());
            pstmt.setString(5, homeCityField.getText());
            pstmt.setString(6, homeStateField.getText());
            pstmt.setString(7, homeZipField.getText());
            pstmt.setString(8, countryField.getText());
            pstmt.setString(9, citizenshipField.getText());
            pstmt.setString(10, mobilePhoneField.getText());
            pstmt.setString(11, emergencyPhoneField.getText());
            pstmt.setString(12, emailField.getText());
            pstmt.setString(13, ssnField.getText());
            pstmt.setDate(14, new java.sql.Date(dobPicker.getDate().getTime())); // Convert date picker value to SQL date
            pstmt.setString(15, (String) genderComboBox.getSelectedItem()); // Get selected item from combo box
            pstmt.setString(16, ethnicField.getText());
            pstmt.setString(17, (String) maritalStatusField.getSelectedItem());
            pstmt.setString(18, primaryHCPField.getText());
            pstmt.setString(19, commentsField.getText());
            pstmt.setString(20, nextOfKinField.getText());
            pstmt.setString(21, nextOfKinRelationshipField.getText());

            // Execute insert query
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close JDBC resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        dispose();
    }
}
