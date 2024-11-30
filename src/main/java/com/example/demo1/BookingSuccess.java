package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class BookingSuccess {
    @FXML private ImageView hotelImageView;
    @FXML private Label hotelNameLabel;
    @FXML private Label hotelAddressLabel;
    @FXML private Label roomTypeLabel;
    @FXML private Label totalPriceLabel;
    @FXML private Label guestNameLabel;
    @FXML private Label guestEmailLabel;
    @FXML private Label guestPhoneLabel;
    @FXML private VBox bookingDetailsBox;

    @FXML
    public void initialize() {
        if (hotelImageView != null) {
            hotelImageView.setFitWidth(200);
            hotelImageView.setFitHeight(150);
            hotelImageView.setPreserveRatio(true);
        }
    }

    public void setHotelDetails(String hotelName, String address, Image hotelImage) {
        hotelImageView.setImage(hotelImage);
        hotelNameLabel.setText(hotelName);
        hotelAddressLabel.setText(address);
    }

    public void setBookingDetails(String roomType, String totalPrice) {
        roomTypeLabel.setText(roomType);
        totalPriceLabel.setText(totalPrice);
    }

    public void setGuestInformation(String firstName, String lastName, String phone, String email) {
        guestNameLabel.setText(firstName + " " + lastName);
        guestPhoneLabel.setText(phone);
        guestEmailLabel.setText(email);
    }
}