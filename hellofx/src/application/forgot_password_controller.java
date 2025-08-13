package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class forgot_password_controller {

    @FXML
    private TextField emailField;
    @FXML
    private TextField newPasswordField;
    @FXML
    private TextField confirmPasswordField;

    @FXML
    private void handleReset() {
        String email = emailField.getText().trim();
        String newPassword = newPasswordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        // Simple validation
        if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "Please fill in all fields!");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert("Error", "Passwords don't match!");
            return;
        }

        try {
            if (updatePassword(email, newPassword)) {
                showAlert("Success", "Password reset successfully!");
                emailField.clear();
                newPasswordField.clear();
                confirmPasswordField.clear();
            } else {
                showAlert("Error", "Email not found!");
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to reset password!");
        }
    }

    private boolean updatePassword(String email, String newPassword) throws IOException {
        File file = new File("users.txt");
        if (!file.exists()) {
            return false;
        }

        List<String> lines = new ArrayList<>();
        boolean found = false;

        // Read all lines
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 2 && parts[0].equals(email)) {
                lines.add(email + "," + newPassword);
                found = true;
            } else {
                lines.add(line);
            }
        }
        reader.close();

        if (found) {
            // Write back to file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
            writer.close();
        }

        return found;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}