package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Signup {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Map<String, User> users = new HashMap<>();
    private static final String USER_FILE = "users.txt";



    @FXML
    public void handleRegister(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Registration Error", "Username and password cannot be empty!", Alert.AlertType.ERROR);
            return;
        }

        if (users.containsKey(username)) {
            showAlert("Registration Error", "Username already exists!", Alert.AlertType.ERROR);
            return;
        }

        User newUser = new User(username, password);
        users.put(username, newUser);
        saveUsers();
        showAlert("Success", "Registration successful! You can now login.", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void initialize() {
        loadUsers();
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.put(parts[0], new User(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            // File might not exist yet, which is fine for first run
            System.out.println("No existing users file found.");
        }
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : users.values()) {
                writer.write(user.getUsername() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not save user data", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleBackToLogin(ActionEvent event) {
        try {
            Hyperlink sourceLink = (Hyperlink) event.getSource();
            Scene currentScene = sourceLink.getScene();
            Stage stage = (Stage) currentScene.getWindow();

            URL resourceUrl = getClass().getResource("/com/example/demo1/login.fxml");
            if (resourceUrl == null) {
                throw new IOException("login.fxml not found in resources");
            }

            FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
            Parent loginRoot = fxmlLoader.load();

            Scene loginScene = new Scene(loginRoot);
            stage.setScene(loginScene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load login page: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
