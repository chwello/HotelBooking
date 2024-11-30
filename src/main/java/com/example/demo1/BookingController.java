package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class BookingController {
    @FXML private VBox bookingDetailsBox;
    @FXML private Label hotelNameLabel;
    @FXML private Label hotelAddressLabel;
    @FXML private Label roomTypeLabel;
    @FXML private Label priceLabel;
    @FXML private Label totalLabel;
    @FXML private ImageView hotelImageView;

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private TextArea specialRequestsField;

    private String roomType;
    private double roomPrice;
    private String hotelName;
    private String hotelAddress;
    private String hotelId;
    private String imagePath;
    private double totalAmount;
    private Image hotelImage;

    @FXML
    private void handleConfirmBooking(ActionEvent event) {
        if (validateForm()) {
            try {
                // Load the booking success FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/bookingSuccess.fxml"));
                Parent bookingSuccessRoot = loader.load();

                // Get the controller and pass the data
                BookingSuccess bookingSuccessController = loader.getController();

                // Set hotel details
                bookingSuccessController.setHotelDetails(hotelName, hotelAddress, hotelImage);

                // Set booking details
                bookingSuccessController.setBookingDetails(
                        roomType,
                        String.format("P%.2f", totalAmount)
                );

                // Set guest information
                bookingSuccessController.setGuestInformation(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        phoneField.getText(),
                        emailField.getText()
                );

                // Get the stage and set the new scene
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(bookingSuccessRoot);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                showError("Error", "Could not process booking: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private boolean validateForm() {
        if (firstNameField == null || lastNameField == null || phoneField == null || emailField == null) {
            showError("Error", "Form fields not properly initialized");
            return false;
        }

        StringBuilder errors = new StringBuilder();

        if (isFieldEmpty(firstNameField)) errors.append("First name is required\n");
        if (isFieldEmpty(lastNameField)) errors.append("Last name is required\n");
        if (isFieldEmpty(phoneField)) errors.append("Phone number is required\n");
        if (isFieldEmpty(emailField)) errors.append("Email address is required\n");

        if (errors.length() > 0) {
            showError("Validation Error", errors.toString());
            return false;
        }
        return true;
    }

    private boolean isFieldEmpty(TextField field) {
        return field.getText() == null || field.getText().trim().isEmpty();
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setBookingDetails(String hotelId, String hotelName, String hotelAddress, String roomType, double price, int nights) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.roomType = roomType;
        this.roomPrice = price;

        hotelNameLabel.setText(hotelName);
        hotelAddressLabel.setText(hotelAddress);

        this.imagePath = switch (hotelId) {
            case "luxury-palace" -> "/com/example/demo1/images/hotel1.jpg";
            case "seaside-resort" -> "/com/example/demo1/images/hotel2.jpg";
            case "mountain-view" -> "/com/example/demo1/images/hotel3.jpg";
            default -> "/com/example/demo1/images/hotel1.jpg";
        };

        try {
            hotelImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            if (hotelImageView != null) {
                hotelImageView.setImage(hotelImage);
            }
        } catch (Exception e) {
            System.out.println("Could not load hotel image: " + imagePath);
            e.printStackTrace();
        }

        updateBookingDetails();
    }

    private void updateBookingDetails() {
        double taxRate = 0.12;
        double taxAmount = roomPrice * taxRate;
        totalAmount = roomPrice + taxAmount;

        bookingDetailsBox.getChildren().clear();
        bookingDetailsBox.getChildren().addAll(
                createDetailLabel("Room Type", roomType),
                createDetailLabel("Price per night", String.format("P%.2f", roomPrice)),
                createDetailLabel("Tax (12%)", String.format("P%.2f", taxAmount)),
                createDetailLabel("Total price (inc. tax)", String.format("P%.2f", totalAmount))
        );
    }

    private Label createDetailLabel(String label, String value) {
        Label detailLabel = new Label(label + ": " + value);
        detailLabel.setStyle("-fx-font-size: 14;");
        return detailLabel;
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/hotelDetails.fxml"));
            Parent root = loader.load();
            HotelDetails hotelDetailsController = loader.getController();
            hotelDetailsController.setHotelData(hotelId);

            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}