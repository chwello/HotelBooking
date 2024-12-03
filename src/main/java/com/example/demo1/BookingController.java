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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class BookingController {
    @FXML private VBox bookingDetailsBox;
    @FXML private Label hotelNameLabel;
    @FXML private Label hotelAddressLabel;
    @FXML private ImageView hotelImageView;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private DatePicker checkInDatePicker;
    @FXML private DatePicker checkOutDatePicker;
    @FXML private TextArea specialRequestsField;

    private String roomType;
    private double roomPrice;
    private String hotelName;
    private String hotelAddress;
    private String hotelId;
    private String imagePath;
    private double totalAmount;
    private Image hotelImage;
    private int numberOfNights;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @FXML
    private void initialize() {
        // Set default values and validation
        checkInDatePicker.setValue(LocalDate.now());
        checkOutDatePicker.setValue(LocalDate.now().plusDays(1));

        // Prevent past dates for check-in
        checkInDatePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0);
            }
        });

        // Prevent dates before check-in for check-out
        checkOutDatePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate checkIn = checkInDatePicker.getValue();
                setDisable(empty || (checkIn != null && date.compareTo(checkIn) <= 0));
            }
        });

        // Add listeners for date changes
        checkInDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                LocalDate checkOut = checkOutDatePicker.getValue();
                if (checkOut == null || checkOut.compareTo(newVal) <= 0) {
                    checkOutDatePicker.setValue(newVal.plusDays(1));
                }
                updateBookingDetails();
            }
        });

        checkOutDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                LocalDate checkIn = checkInDatePicker.getValue();
                if (checkIn != null && newVal.compareTo(checkIn) <= 0) {
                    checkOutDatePicker.setValue(checkIn.plusDays(1));
                }
                updateBookingDetails();
            }
        });
    }

    @FXML
    private void handleConfirmBooking(ActionEvent event) {
        if (validateForm()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/bookingSuccess.fxml"));
                Parent bookingSuccessRoot = loader.load();

                BookingSuccess bookingSuccessController = loader.getController();
                bookingSuccessController.setHotelDetails(hotelName, hotelAddress, hotelImage);
                bookingSuccessController.setBookingDetails(
                        roomType,
                        String.format("P%.2f", totalAmount)
                );
                bookingSuccessController.setGuestInformation(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        phoneField.getText(),
                        emailField.getText()
                );
                // Add dates to booking success
                bookingSuccessController.setDates(
                        checkInDatePicker.getValue(),
                        checkOutDatePicker.getValue(),
                        numberOfNights
                );

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
        if (firstNameField == null || lastNameField == null || phoneField == null ||
                emailField == null || checkInDatePicker == null || checkOutDatePicker == null) {
            showError("Error", "Form fields not properly initialized");
            return false;
        }

        StringBuilder errors = new StringBuilder();

        if (isFieldEmpty(firstNameField)) errors.append("First name is required\n");
        if (isFieldEmpty(lastNameField)) errors.append("Last name is required\n");
        if (isFieldEmpty(phoneField)) errors.append("Phone number is required\n");
        if (isFieldEmpty(emailField)) errors.append("Email address is required\n");
        if (checkInDatePicker.getValue() == null) errors.append("Check-in date is required\n");
        if (checkOutDatePicker.getValue() == null) errors.append("Check-out date is required\n");

        // Validate dates
        if (checkInDatePicker.getValue() != null && checkOutDatePicker.getValue() != null) {
            if (checkInDatePicker.getValue().isBefore(LocalDate.now())) {
                errors.append("Check-in date cannot be in the past\n");
            }
            if (checkOutDatePicker.getValue().compareTo(checkInDatePicker.getValue()) <= 0) {
                errors.append("Check-out date must be after check-in date\n");
            }
        }

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

    public void setBookingDetails(String hotelId, String hotelName, String hotelAddress,
                                  String roomType, double price, int nights,
                                  LocalDate checkInDate, LocalDate checkOutDate) {  // Added checkOutDate parameter
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.roomType = roomType;
        this.roomPrice = price;
        this.numberOfNights = nights;

        // Set both dates directly from HotelDetails selection
        if (checkInDatePicker != null && checkOutDatePicker != null) {
            checkInDatePicker.setValue(checkInDate);
            checkOutDatePicker.setValue(checkOutDate);
        }

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
        // Calculate number of nights
        if (checkInDatePicker.getValue() != null && checkOutDatePicker.getValue() != null) {
            numberOfNights = (int) ChronoUnit.DAYS.between(
                    checkInDatePicker.getValue(),
                    checkOutDatePicker.getValue()
            );
        }

        // Calculate prices
        double taxRate = 0.12;
        double subtotal = roomPrice * numberOfNights;
        double taxAmount = subtotal * taxRate;
        totalAmount = subtotal + taxAmount;

        // Update UI with formatted dates
        bookingDetailsBox.getChildren().clear();
        bookingDetailsBox.getChildren().addAll(
                createDetailLabel("Room Type", roomType),
                createDetailLabel("Price per night", String.format("P%.2f", roomPrice)),
                createDetailLabel("Check-in", checkInDatePicker.getValue().format(dateFormatter)),
                createDetailLabel("Check-out", checkOutDatePicker.getValue().format(dateFormatter)),
                createDetailLabel("Number of nights", String.valueOf(numberOfNights)),
                createDetailLabel("Subtotal", String.format("P%.2f", subtotal)),
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