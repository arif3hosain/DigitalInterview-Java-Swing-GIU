package com.app.model.gui.medicineHistory;

import com.app.DBUtils.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddMedicalHistoryDialog extends JDialog {
    private JTextField  tobaccoField, tobaccoQuantityField, tobaccoDurationField,
            alcoholField, alcoholQuantityField, alcoholDurationField, drugField, drugTypeField,
            drugDurationField;

    private JComboBox<String> bloodTypeCombo, rhCombo;

    private JButton saveButton;
    private JButton cancelButton;
    private String patientId;

    public AddMedicalHistoryDialog(Frame parent, String id) {
        super(parent, "Add New Medical History", true);
        setSize(400, 400);
        patientId = id;
        // Initialize components
        initializeComponents();

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

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMedicalHistory();
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

    private void addComponentsToDialog() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
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

    private void saveMedicalHistory() {
        String bloodType = (String) bloodTypeCombo.getSelectedItem();
        String rh = (String) rhCombo.getSelectedItem();
        if (bloodType == null || bloodType.isEmpty() || rh == null || rh.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Blood Type and Rh are required fields.");
            return;
        }
        try {
            // Establish database connection
            Connection connection = DBConnection.getConnection();

            // Prepare SQL statement
            String sql = "INSERT INTO medicalhistory (PatientID, Tobacco, TobaccoQuantity, Tobaccoduration, " +
                    "Alcohol, AlcoholQuantity, Alcoholduration, Drug, DrugType, Drugduration, " +
                    "BloodType, Rh, deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set values from text fields and combo boxes
            statement.setInt(1, Integer.parseInt(patientId));
            statement.setString(2, tobaccoField.getText());
            statement.setString(3, tobaccoQuantityField.getText());
            statement.setString(4, tobaccoDurationField.getText());
            statement.setString(5, alcoholField.getText());
            statement.setString(6, alcoholQuantityField.getText());
            statement.setString(7, alcoholDurationField.getText());
            statement.setString(8, drugField.getText());
            statement.setString(9, drugTypeField.getText());
            statement.setString(10, drugDurationField.getText());
            statement.setString(11, (String) bloodTypeCombo.getSelectedItem());
            statement.setString(12, (String) rhCombo.getSelectedItem());

            // Execute the query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Medical history saved successfully.");
                dispose(); // Close the dialog
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save medical history.");
            }

            // Close the connection and statement
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error" + e.getMessage());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid Patient ID. Please enter a valid number.");
        }
    }
}
