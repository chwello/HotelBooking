
package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import java.io.IOException;
import java.util.Objects;

public class HotelDetails {
    @FXML private ImageView hotelImage;
    @FXML private Label hotelNameLabel;
    @FXML private Label locationLabel;
    @FXML private Label overviewLabel;
    @FXML private FlowPane amenitiesContainer;
    @FXML private VBox highlightsContainer;
    @FXML private VBox roomsContainer;

    @FXML
    public void initialize() {
        // Don't set up rooms in initialize
    }

    public void setHotelData(String hotelId) {
        switch (hotelId) {
            case "luxury-palace" -> {
                hotelNameLabel.setText("Luxury Palace Hotel Singapore");
                locationLabel.setText("1 Marina Boulevard, 018989 Singapore, Singapore");
                hotelImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo1/images/hotel1.jpg"))));

                overviewLabel.setText("Experience the epitome of luxury and sophistication at the Luxury Palace Hotel Singapore. " +
                        "Nestled in the heart of Marina Bay, this opulent haven offers panoramic city views, world-class dining, " +
                        "and unparalleled amenities designed for an unforgettable stay. Perfect for leisure and business travelers alike, " +
                        "the hotel boasts state-of-the-art facilities, a serene spa, and exceptional service tailored to your every need.");

                setAmenities(new String[]{  // Changed from amenitiesTags to setAmenities
                        "Rooftop Infinity Pool",
                        "Michelin-Starred Restaurants",
                        "State-of-the-Art Fitness Center",
                        "24/7 Concierge Service",
                        "Exclusive Club Lounge Access",
                        "Luxury Spa Services",
                        "Business Center",
                        "Conference Facilities",
                        "Valet Parking",
                        "Airport Transfer Service"
                });


                setHighlights(new String[]{
                        "Location rating score of 9.6!",
                        "Dining experience rating of 9.8",
                        "Service excellence rating of 9.7",
                        "Amenities rating of 9.5"
                });




                // Setup rooms specific to Luxury Palace
                setupRoomsForLuxuryPalace();
            }
            case "seaside-resort" -> {
                hotelNameLabel.setText("Seaside Resort");
                locationLabel.setText("Boracay Island, Philippines");
                hotelImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo1/images/hotel2.jpg"))));

                // Set overview and other data...

                // Setup rooms specific to Seaside Resort
                setupRoomsForSeasideResort();
                setupRoomsForLuxuryPalace();
            }
            case "mountain-view" -> {
                hotelNameLabel.setText("Mountain View Lodge");
                locationLabel.setText("Baguio City, Philippines");
                hotelImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo1/images/hotel3.jpg"))));

                // Set overview and other data...

                // Setup rooms specific to Mountain View
                setupRoomsForMountainView();
            }
        }
    }

    private void setupRoomsForLuxuryPalace() {
        roomsContainer.getChildren().clear();
        addRoomCard(
                "Deluxe Room",
                "Spacious room with city view, featuring a king-size bed, work desk, and modern amenities.",
                2024.00,
                new String[]{"Free Cancellation", "Breakfast Included"},
                "/com/example/demo1/images/hotel1.jpg"  // Using the same image as hotel for now
        );

        addRoomCard(
                "Executive Suite",
                "Luxurious suite with separate living area and panoramic city views.",
                2624.00,
                new String[]{"Free Cancellation", "Breakfast Included", "Lounge Access"},
                "/com/example/demo1/images/hotel1.jpg"
        );
    }

    private void setupRoomsForSeasideResort() {
        roomsContainer.getChildren().clear();
        addRoomCard(
                "Beach View Room",
                "Beautiful room with ocean views and private balcony.",
                2224.00,
                new String[]{"Free Cancellation", "Breakfast Included", "Ocean View"},
                "/com/example/demo1/images/hotel2.jpg"
        );

        addRoomCard(
                "Beachfront Suite",
                "Spacious suite with direct beach access.",
                3224.00,
                new String[]{"Free Cancellation", "Breakfast Included", "Private Beach Access"},
                "/com/example/demo1/images/hotel2.jpg"
        );
    }

    private void setupRoomsForMountainView() {
        roomsContainer.getChildren().clear();
        addRoomCard(
                "Mountain View Room",
                "Cozy room with breathtaking mountain views.",
                1824.00,
                new String[]{"Free Cancellation", "Breakfast Included", "Mountain View"},
                "/com/example/demo1/images/hotel3.jpg"
        );

        addRoomCard(
                "Mountain Suite",
                "Spacious suite with panoramic mountain views.",
                2424.00,
                new String[]{"Free Cancellation", "Breakfast Included", "Fireplace"},
                "/com/example/demo1/images/hotel3.jpg"
        );
    }

    private void setAmenities(String[] amenities) {
        amenitiesContainer.getChildren().clear();
        for (String amenity : amenities) {
            HBox amenityBox = new HBox(10);
            amenityBox.setStyle("-fx-padding: 10; -fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 4;");
            amenityBox.setPrefWidth(200);
            amenityBox.setAlignment(Pos.CENTER_LEFT);

            // Create icon (you can use • as a simple bullet point)
            Label iconLabel = new Label("•");
            iconLabel.setStyle("-fx-text-fill: #1a73e8; -fx-font-size: 18;");

            // Create amenity label
            Label amenityLabel = new Label(amenity);
            amenityLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #333333;");

            amenityBox.getChildren().addAll(iconLabel, amenityLabel);
            amenitiesContainer.getChildren().add(amenityBox);
        }
    }

    private void setHighlights(String[] highlights) {
        highlightsContainer.getChildren().clear();
        for (String highlight : highlights) {
            // Create container for each highlight
            HBox highlightBox = new HBox(10);
            highlightBox.setAlignment(Pos.CENTER_LEFT);

            // Create location icon
            Label icon = new Label("✓");
            icon.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 16; -fx-font-weight: bold;");

            // Create highlight text
            Label highlightLabel = new Label(highlight);
            highlightLabel.setStyle("-fx-font-size: 14; -fx-wrap-text: true;");
            highlightLabel.setWrapText(true);

            // Add icon and text to container
            highlightBox.getChildren().addAll(icon, highlightLabel);

            // Add to highlights container
            highlightsContainer.getChildren().add(highlightBox);
        }
    }

    private void addRoomCard(String type, String description, double price, String[] amenities, String imagePath) {
        try {
            VBox card = new VBox(20);
            card.getStyleClass().add("room-card");
            card.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-color: #e0e0e0; -fx-border-radius: 5;");

            HBox content = new HBox(20);

            // Room Image with error handling
            ImageView imageView = new ImageView();
            try {
                Image image = new Image(getClass().getResourceAsStream(imagePath));
                imageView.setImage(image);
                imageView.setFitWidth(200);
                imageView.setFitHeight(150);
                imageView.setStyle("-fx-background-radius: 5;");
            } catch (Exception e) {
                System.out.println("Could not load room image: " + imagePath);
            }

            // Room Details
            VBox details = new VBox(10);
            HBox.setHgrow(details, Priority.ALWAYS);

            Label typeLabel = new Label(type);
            typeLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

            Label descLabel = new Label(description);
            descLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #666666;");
            descLabel.setWrapText(true);

            // Amenities Tags
            FlowPane amenitiesTags = new FlowPane(10, 10);
            for (String amenity : amenities) {
                Label tag = new Label(amenity);
                tag.setStyle("-fx-padding: 5 10; -fx-background-color: #e8f5e9; -fx-text-fill: #2e7d32; " +
                        "-fx-background-radius: 4; -fx-font-size: 12;");
                amenitiesTags.getChildren().add(tag);
            }

            details.getChildren().addAll(typeLabel, descLabel, amenitiesTags);

            // Price and Book Section
            VBox priceSection = new VBox(5);
            priceSection.setAlignment(Pos.TOP_RIGHT);
            priceSection.setMinWidth(200);

            Label priceLabel = new Label(String.format("P%.2f", price));
            priceLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #1a73e8;");

            Button selectButton = new Button("Select");
            selectButton.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; " +
                    "-fx-padding: 8 20; -fx-background-radius: 4;");
            selectButton.setOnAction(e -> handleRoomSelection(type, price));

            priceSection.getChildren().addAll(priceLabel, selectButton);

            content.getChildren().addAll(imageView, details, priceSection);
            card.getChildren().add(content);
            roomsContainer.getChildren().add(card);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleRoomSelection(String roomType, double price) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Room Selection");
        alert.setHeaderText("Confirm Room Selection");
        alert.setContentText(String.format("Would you like to book the %s for $%.2f?",
                roomType, price));

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                proceedToBooking(roomType, price);
            }
        });
    }

    private void proceedToBooking(String roomType, double price) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Booking Confirmation");
        alert.setHeaderText("Thank you for your selection!");
        alert.setContentText(String.format("Your booking for %s (Price: $%.2f) is being processed.",
                roomType, price));
        alert.showAndWait();
    }

    @FXML
    private void handleBooking() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/confirmBooking.fxml"));
            Scene currentScene = hotelNameLabel.getScene();
            Scene newScene = new Scene(loader.load());
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) hotelNameLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
