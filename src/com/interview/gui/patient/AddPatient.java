package com.interview.gui.patient;


import com.interview.DBUtils.DBConnection;
import com.interview.other.Msg;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddPatient extends JDialog {
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

    public AddPatient(JFrame parent) {
        super(parent, "Add New Patient", true);
        setSize(400, 600);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        add(panel);

        panel.add(new JLabel("Last Nam*e"));
        lastNameField = new JTextField();
        panel.add(lastNameField);

        panel.add(new JLabel("Previous Last Name"));
        prevLastNameField = new JTextField();
        panel.add(prevLastNameField);

        panel.add(new JLabel("First Name*"));
        firstNameField = new JTextField();
        panel.add(firstNameField);

        panel.add(new JLabel("Home Address"));
        homeAddressField = new JTextField();
        panel.add(homeAddressField);

        panel.add(new JLabel("Home City"));
        homeCityField = new JTextField();
        panel.add(homeCityField);

        panel.add(new JLabel("Home State/Province/Region"));
        homeStateField = new JTextField();
        panel.add(homeStateField);

        panel.add(new JLabel("Home Zip"));
        homeZipField = new JTextField();
        panel.add(homeZipField);

        panel.add(new JLabel("Country"));
        countryField = new JTextField();
        panel.add(countryField);

        panel.add(new JLabel("Citizenship"));
        citizenshipField = new JTextField();
        panel.add(citizenshipField);

        panel.add(new JLabel("Mobile Phone"));
        mobilePhoneField = new JTextField();
        panel.add(mobilePhoneField);

        panel.add(new JLabel("Emergency Phone"));
        emergencyPhoneField = new JTextField();
        panel.add(emergencyPhoneField);

        panel.add(new JLabel("Email Address"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("SSN"));
        ssnField = new JTextField();
        panel.add(ssnField);

        panel.add(new JLabel("Date of Birth"));
        dobPicker = new JDateChooser(); // Date picker for DOB
        panel.add(dobPicker);

        panel.add(new JLabel("Gender"));
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"}); // Combo box for gender
        panel.add(genderComboBox);

        JButton saveButton = new JButton("Save");
        panel.add(saveButton);

        panel.add(new JLabel("Ethnic Association"));
        ethnicField = new JTextField();
        panel.add(ethnicField);

        panel.add(new JLabel("Marital Status"));
        maritalStatusField = new JComboBox<>(new String[]{"Married", "Single"}); // Combo box for gender
        panel.add(maritalStatusField);

        panel.add(new JLabel("Current Primary HCP"));
        primaryHCPField = new JTextField();
        panel.add(primaryHCPField);

        panel.add(new JLabel("Comments"));
        commentsField = new JTextField();
        panel.add(commentsField);

        panel.add(new JLabel("Next of Kin"));
        nextOfKinField = new JTextField();
        panel.add(nextOfKinField);

        panel.add(new JLabel("Next of Kin Relationship"));
        nextOfKinRelationshipField = new JTextField();
        panel.add(nextOfKinRelationshipField);

//        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                savePatientRecord();

            }
        });
        panel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cancelButton);
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
            JOptionPane.showMessageDialog(this, Msg.Save, "Success", JOptionPane.INFORMATION_MESSAGE);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddPatient dialog = new AddPatient(null);
            dialog.setVisible(true);
        });
    }
}
