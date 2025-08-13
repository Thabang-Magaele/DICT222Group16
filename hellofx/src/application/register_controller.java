package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class register_controller {

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField idNumberField;
    @FXML
    private TextField homeAddressField;
    @FXML
    private TextField vehicleRegistrationField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    @FXML
    private void handleCreateAccount() {
        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();
        String idNumber = idNumberField.getText().trim();
        String homeAddress = homeAddressField.getText().trim();
        String vehicleRegistration = vehicleRegistrationField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        // Simple validation - check if fields are empty
        if (name.isEmpty() || surname.isEmpty() || idNumber.isEmpty() || 
            homeAddress.isEmpty() || vehicleRegistration.isEmpty() || 
            email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in all fields!");
            return;
        }

        // Basic email validation
        if (!email.contains("@") || !email.contains(".")) {
            showAlert("Error", "Please enter a valid email address!");
            return;
        }

        // Basic password validation
        if (password.length() < 3) {
            showAlert("Error", "Password must be at least 3 characters long!");
            return;
        }

        try {
            // Save email and password to users.txt file
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("users.txt"), true));
            writer.write(email + "," + password);
            writer.newLine();
            writer.close();

            showAlert("Success", "Account created successfully!\nYou can now login with your email and password.");
            
            // Clear all fields
            clearFields();

        } catch (IOException e) {
            showAlert("Error", "Failed to create account! Please try again.");
        }
    }

    private void clearFields() {
        nameField.clear();
        surnameField.clear();
        idNumberField.clear();
        homeAddressField.clear();
        vehicleRegistrationField.clear();
        emailField.clear();
        passwordField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}