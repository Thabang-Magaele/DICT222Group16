package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class login_controller {

    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    // Login Button
    @FXML
    private void handleClick() throws IOException {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        // Simple validation
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in both fields!");
            return;
        }

        if (checkCredentials(email, password)) {
            openNewWindow("fuel.fxml", "Fuel System");
        } else {
            showAlert("Login Failed", "Invalid email or password!");
        }
    }

    // Handle Forgot Password Click
    @FXML
    private void handleForgotPassword() throws IOException {
        openNewWindow("forgot_password.fxml", "Forgot Password");
    }

    // Handle Create Account Click
    @FXML
    private void handleCreateAccount() throws IOException {
        openNewWindow("register.fxml", "Create Account");
    }

    // Open new window
    private void openNewWindow(String fxmlFile, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Check credentials from users.txt
    private boolean checkCredentials(String email, String password) {
        File file = new File("users.txt");
        
        // If file doesn't exist, no users registered yet
        if (!file.exists()) {
            return false;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    if (parts[0].equals(email) && parts[1].equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Show alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}