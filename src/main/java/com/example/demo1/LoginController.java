package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final Map<String, User> users = new HashMap<>();
    private static final String USER_FILE = "users.txt";

    @FXML
    public void initialize() {
        loadUsers();
    }




    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.getPassword().equals(password)) {
                try {
                    Button sourceButton = (Button) event.getSource();
                    Scene currentScene = sourceButton.getScene();
                    Stage stage = (Stage) currentScene.getWindow();

                    URL resourceUrl = getClass().getResource("/com/example/demo1/home.fxml");
                    if (resourceUrl == null) {
                        throw new IOException("home.fxml not found in resources");
                    }

                    FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
                    Parent homeRoot = fxmlLoader.load();

                    // Pass the username to HomeController
                    HomeController homeController = fxmlLoader.getController();
                    homeController.setUsername(username);

                    Scene homeScene = new Scene(homeRoot);
                    stage.setScene(homeScene);
                    stage.setTitle("Home Page - Welcome " + username);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Error", "Could not load home page: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Login Failed", "Invalid password.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Login Failed", "User not found. Please register first.", Alert.AlertType.ERROR);
        }
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

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void handleSignup(ActionEvent event) {
        try {
            Hyperlink sourceLink = (Hyperlink) event.getSource();
            Scene currentScene = sourceLink.getScene();
            Stage stage = (Stage) currentScene.getWindow();

            URL resourceUrl = getClass().getResource("/com/example/demo1/signup.fxml");
            if (resourceUrl == null) {
                throw new IOException("signup.fxml not found in resources");
            }

            FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
            Parent signupRoot = fxmlLoader.load();

            Scene signupScene = new Scene(signupRoot);
            stage.setScene(signupScene);
            stage.setTitle("Sign Up");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load signup page: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}