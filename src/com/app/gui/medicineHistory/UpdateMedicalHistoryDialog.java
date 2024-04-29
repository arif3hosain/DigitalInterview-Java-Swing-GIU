package com.app.model.gui.medicineHistory;


import com.app.DBUtils.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UpdateMedicalHistoryDialog extends JDialog {
    private JTextField  tobaccoField, tobaccoQuantityField, tobaccoDurationField,
            alcoholField, alcoholQuantityField, alcoholDurationField, drugField, drugTypeField,
            drugDurationField;
    String globalMHID;

    private JComboBox<String> bloodTypeCombo, rhCombo;

    private JButton updateButton;
    private JButton cancelButton;
    private String selectedPatientId;

    public UpdateMedicalHistoryDialog(Frame parent, String id) {
        super(parent, "Update Medical History", true);
        setSize(400, 400);
        globalMHID =id;
        // Initialize components
        initializeComponents();

        // Populate fields with data from database
        populateFields();

        // Add components to the dialog
        addComponentsToDialog();

        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        tobaccoField = new JTextField(10);
        tobaccoQuantityField = new JTextField(10);
        tobaccoDurationField = new JTextField(10);
        alcoholField = new JTextField(10);
        alcoholQuantityField = new JTextField(10);
        alcoholDurationField = new JTextField(10);
        drugField = new JTextField(10);
        drugTypeField = new JTextField(10);
        drugDurationField = new JTextField(10);

        // Blood Type Combo Box
        String[] bloodTypes = {"A", "B", "AB", "O"};
        bloodTypeCombo = new JComboBox<>(bloodTypes);

        // Rh Combo Box
        String[] rhValues = {"+", "-"};
        rhCombo = new JComboBox<>(rhValues);

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMedicalHistory();
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog
            }
        });
    }

    private void populateFields() {
        // Retrieve data from the database for ID 1 and populate the fields
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "SELECT * FROM medicalhistory WHERE GeneralMedicalHistoryID = "+globalMHID;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                tobaccoField.setText(resultSet.getString("Tobacco"));
                tobaccoQuantityField.setText(resultSet.getString("TobaccoQuantity"));
                tobaccoDurationField.setText(resultSet.getString("Tobaccoduration"));
                alcoholField.setText(resultSet.getString("Alcohol"));
                alcoholQuantityField.setText(resultSet.getString("AlcoholQuantity"));
                alcoholDurationField.setText(resultSet.getString("Alcoholduration"));
                drugField.setText(resultSet.getString("Drug"));
                drugTypeField.setText(resultSet.getString("DrugType"));
                drugDurationField.setText(resultSet.getString("Drugduration"));
                bloodTypeCombo.setSelectedItem(resultSet.getString("BloodType"));
                rhCombo.setSelectedItem(resultSet.getString("Rh"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error" + e.getMessage());
        }
    }

    private void addComponentsToDialog() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(15, 2));
        mainPanel.add(new JLabel("Tobacco"));
        mainPanel.add(tobaccoField);
        mainPanel.add(new JLabel("Tobacco Quantity"));
        mainPanel.add(tobaccoQuantityField);
        mainPanel.add(new JLabel("Tobacco Duration"));
        mainPanel.add(tobaccoDurationField);
        mainPanel.add(new JLabel("Alcohol"));
        mainPanel.add(alcoholField);
        mainPanel.add(new JLabel("Alcohol Quantity"));
        mainPanel.add(alcoholQuantityField);
        mainPanel.add(new JLabel("Alcohol Duration"));
        mainPanel.add(alcoholDurationField);
        mainPanel.add(new JLabel("Drug"));
        mainPanel.add(drugField);
        mainPanel.add(new JLabel("Drug Type"));
        mainPanel.add(drugTypeField);
        mainPanel.add(new JLabel("Drug Duration"));
        mainPanel.add(drugDurationField);
        mainPanel.add(new JLabel("Blood Type*"));
        mainPanel.add(bloodTypeCombo);
        mainPanel.add(new JLabel("Rh*"));
        mainPanel.add(rhCombo);
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    private void updateMedicalHistory() {
        try {
            Connection connection = DBConnection.getConnection();

            String sql = "UPDATE medicalhistory SET Tobacco = ?, TobaccoQuantity = ?, Tobaccoduration = ?, " +
                    "Alcohol = ?, AlcoholQuantity = ?, Alcoholduration = ?, Drug = ?, DrugType = ?, Drugduration = ?, " +
                    "BloodType = ?, Rh = ? WHERE GeneralMedicalHistoryID = "+globalMHID;
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, tobaccoField.getText());
            statement.setString(2, tobaccoQuantityField.getText());
            statement.setString(3, tobaccoDurationField.getText());
            statement.setString(4, alcoholField.getText());
            statement.setString(5, alcoholQuantityField.getText());
            statement.setString(6, alcoholDurationField.getText());
            statement.setString(7, drugField.getText());
            statement.setString(8, drugTypeField.getText());
            statement.setString(9, drugDurationField.getText());
            statement.setString(10, (String) bloodTypeCombo.getSelectedItem());
            statement.setString(11, (String) rhCombo.getSelectedItem());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Medical history updated successfully.");
                dispose(); // Close the dialog
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update medical history.");
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error" + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Patient ID. Please enter a valid number.");
        }
    }
}
