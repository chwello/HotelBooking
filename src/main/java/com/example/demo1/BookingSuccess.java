package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDate;


public class BookingSuccess {
    @FXML private ImageView hotelImageView;
    @FXML private Label hotelNameLabel;
    @FXML private Label hotelAddressLabel;
    @FXML private Label roomTypeLabel;
    @FXML private Label totalPriceLabel;
    @FXML private Label guestNameLabel;
    @FXML private Label guestEmailLabel;
    @FXML private Label guestPhoneLabel;

    @FXML private Label checkInDateLabel;
    @FXML private Label checkOutDateLabel;
    @FXML private Label numberOfNightsLabel;

    public void setDates(LocalDate checkIn, LocalDate checkOut, int nights) {
        checkInDateLabel.setText("Check-in: " + checkIn.toString());
        checkOutDateLabel.setText("Check-out: " + checkOut.toString());
        numberOfNightsLabel.setText("Duration: " + nights + (nights > 1 ? " nights" : " night"));
    }


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