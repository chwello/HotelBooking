package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

    private String roomType;
    private double roomPrice;
    private String hotelName;
    private String hotelAddress;
    private String hotelId;
    private String imagePath;

    @FXML
    public void initialize() {
        // Make sure the ImageView has proper dimensions
        if (hotelImageView != null) {
            hotelImageView.setFitWidth(360);
            hotelImageView.setFitHeight(240);
            hotelImageView.setPreserveRatio(true);
        }
    }

    public void setBookingDetails(String hotelId, String hotelName, String hotelAddress, String roomType, double price, int nights) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.roomType = roomType;
        this.roomPrice = price;

        // Update the UI elements
        hotelNameLabel.setText(hotelName);
        hotelAddressLabel.setText(hotelAddress);

        // Set the hotel image based on the hotel ID
        this.imagePath = switch (hotelId) {
            case "luxury-palace" -> "/com/example/demo1/images/hotel1.jpg";
            case "seaside-resort" -> "/com/example/demo1/images/hotel2.jpg";
            case "mountain-view" -> "/com/example/demo1/images/hotel3.jpg";
            default -> "/com/example/demo1/images/hotel1.jpg";
        };

        try {
            // Debug print
            System.out.println("Loading image from path: " + imagePath);

            // Create image and set it
            Image hotelImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            if (hotelImage.isError()) {
                System.out.println("Error loading image: " + hotelImage.getException());
            }

            if (hotelImageView != null) {
                hotelImageView.setImage(hotelImage);
            } else {
                System.out.println("hotelImageView is null");
            }
        } catch (Exception e) {
            System.out.println("Could not load hotel image: " + imagePath);
            e.printStackTrace();
        }

        updateBookingDetails();
    }

    private void updateBookingDetails() {
        // Room Type
        roomTypeLabel = new Label("Room Type: " + roomType);
        roomTypeLabel.setStyle("-fx-font-size: 14;");

        // Price per night
        priceLabel = new Label(String.format("Price per night: P%.2f", roomPrice));
        priceLabel.setStyle("-fx-font-size: 14;");

        // Calculate tax and total
        double taxRate = 0.12; // 12% tax
        double taxAmount = roomPrice * taxRate;
        double totalAmount = roomPrice + taxAmount;

        // Total price with tax
        totalLabel = new Label(String.format("Total price (inc. tax): P%.2f", totalAmount));
        totalLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        // Add all labels to the booking details box
        bookingDetailsBox.getChildren().clear();
        bookingDetailsBox.getChildren().addAll(
                roomTypeLabel,
                priceLabel,
                new Label(String.format("Tax (12%%): P%.2f", taxAmount)),
                totalLabel
        );
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/hotelDetails.fxml"));
            Parent root = loader.load();

            // Get the controller and set the hotel data using the stored ID
            HotelDetails hotelDetailsController = loader.getController();
            hotelDetailsController.setHotelData(hotelId);

            Scene scene = new Scene(root);
            Stage stage = (Stage) bookingDetailsBox.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}