package com.example.evan_mulcare_assignment_two.Controllers;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.example.evan_mulcare_assignment_two.Controllers.Helpers.FormatDate.formatDate;
import static com.example.evan_mulcare_assignment_two.Controllers.Helpers.FormatTime.formatTime;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evan_mulcare_assignment_two.Models.Booking;
import com.example.evan_mulcare_assignment_two.Models.CurrentUserSingleton;
import com.example.evan_mulcare_assignment_two.Models.DatabaseHandlerSingleton;
import com.example.evan_mulcare_assignment_two.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UpcomingBookingController {
    private final View view;
    public UpcomingBookingController(View view, Context context) {
        this.view = view;
        initialize();
    }

    private void initialize() {
        RecyclerView verticalRecyclerView = view.findViewById(R.id.verticalRecyclerView);
        TextView noBookingHeading = view.findViewById(R.id.noBookingHeading);
        TextView noBookingSubtext = view.findViewById(R.id.noBookingSubtext);

        List<Booking> upcomingBookings = getUpcomingBookings();
        showBookingsView(verticalRecyclerView,noBookingHeading,noBookingSubtext,upcomingBookings);
    }

    private List<Booking> getUpcomingBookings() {
        // Get bookings for the current user
        List<Booking> bookingList =  DatabaseHandlerSingleton.getInstance(view.getContext()).getBookingsForUser(CurrentUserSingleton.getInstance().getCurrentUser().getUsername());

        // Get the current date and time
        Date currentDate = new Date();
        String formattedDate = formatDate(currentDate);
        String formattedTime = formatTime(currentDate);

        List<Booking> upcomingBookings = new ArrayList<>();
        for (Booking booking : bookingList) {
            if (isBookingUpcoming(booking, formattedDate, formattedTime)) {
                upcomingBookings.add(booking);
            }
        }

        return upcomingBookings;
    }

    private boolean isBookingUpcoming(Booking booking, String formattedDate, String formattedTime) {
        SimpleDateFormat bookingDateTimeFormat = new SimpleDateFormat("dd-MM-yyyy hh:mma", Locale.getDefault());
        String bookingDateTime = booking.getBookingDate() + " " + booking.getBookingTime();

        try {
            Date bookingDate = bookingDateTimeFormat.parse(bookingDateTime);
            String formattedBookingDate = formatDate(bookingDate);
            String formattedBookingTime = formatTime(bookingDate);

            return (formattedBookingDate.equals(formattedDate) && formattedBookingTime.compareTo(formattedTime) >= 0) ||
                    formattedBookingDate.compareTo(formattedDate) > 0;
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing booking date and time", e);
            return false;
        }
    }
    private void showBookingsView(RecyclerView verticalRecyclerView, TextView noBookingHeading, TextView noBookingSubtext, List<Booking> upcomingBookings) {
        if (upcomingBookings != null && !upcomingBookings.isEmpty()) {
            verticalRecyclerView.setVisibility(View.VISIBLE);
            noBookingHeading.setVisibility(View.GONE);
            noBookingSubtext.setVisibility(View.GONE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            verticalRecyclerView.setLayoutManager(layoutManager);
            BookingVerticalAdapter bookingAdapter = new BookingVerticalAdapter(upcomingBookings, view.getContext());
            verticalRecyclerView.setAdapter(bookingAdapter);
        } else {
            showNoBookingsView(verticalRecyclerView, noBookingHeading, noBookingSubtext);
        }
    }
    private void showNoBookingsView(RecyclerView verticalRecyclerView, TextView noBookingHeading, TextView noBookingSubtext) {
        verticalRecyclerView.setVisibility(View.GONE);
        noBookingHeading.setVisibility(View.VISIBLE);
        noBookingSubtext.setVisibility(View.VISIBLE);
    }
}
