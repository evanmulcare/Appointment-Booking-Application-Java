package com.example.evan_mulcare_assignment_two.Models;

public class Booking {
    private String title;
    private int imageResourceId;
    private String userName;
    private String bookingTime;
    private String bookingDate;

    public Booking(String title, int imageResourceId, String userName, String bookingTime, String bookingDate) {
        this.title = title;
        this.imageResourceId = imageResourceId;
        this.userName = userName;
        this.bookingTime = bookingTime;
        this.bookingDate = bookingDate;
    }

    public boolean isBooked() {
        return userName != null && !userName.isEmpty();
    }

    public String getTitle() {
        return title;
    }
    public int getImageResourceId() {
        return imageResourceId;
    }
    public String getBookingTime() {
        return bookingTime;
    }
    public String getUserName() {
        return userName;
    }

    public String getBookingDate() {
        return bookingDate;
    }
}
