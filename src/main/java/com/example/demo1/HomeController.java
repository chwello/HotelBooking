package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class HomeController {
    @FXML
    private Label welcomeLabel;

    @FXML
    private Button logoutButton;

    private String username;

    @FXML
    public void initialize() {
        // Any initialization code
    }

    public void setUsername(String username) {
        this.username = username;
        welcomeLabel.setText("Welcome, " + username + "!");
        welcomeLabel.setStyle("-fx-text-fill: #2196F3;");
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleHotelDetails() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hotel Details");

    }

    @FXML
    private void handleHotelsMenu() {
        System.out.println("Hotels link clicked!");
        // Add navigation or logic here
    }

    @FXML
    private void handleBookingsMenu() {
        System.out.println("Bookings link clicked!");
        // Add navigation or logic here
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleHotelDetails(MouseEvent event) {
        try {
            // Get the clicked VBox and its hotel ID
            Node source = (Node) event.getSource();
            String hotelId = (String) source.getUserData();

            // Load the hotel details FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hotelDetails.fxml"));
            Parent root = loader.load();

            // Get the controller and set the hotel data
            HotelDetails detailsController = loader.getController();
            detailsController.setHotelData(hotelId);

            // Create new scene and show it
            Scene scene = new Scene(root);
            Stage stage = (Stage) source.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

