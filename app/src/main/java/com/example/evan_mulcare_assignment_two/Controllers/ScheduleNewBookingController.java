package com.example.evan_mulcare_assignment_two.Controllers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.evan_mulcare_assignment_two.Models.Booking;
import com.example.evan_mulcare_assignment_two.Models.DatabaseHandlerSingleton;
import com.example.evan_mulcare_assignment_two.R;
import com.example.evan_mulcare_assignment_two.Views.NavigationBar;
import com.example.evan_mulcare_assignment_two.Views.ScheduleNewBookingActivity;

public class ScheduleNewBookingController {
    private final ScheduleNewBookingActivity view;
    private Booking foundBooking;

    public ScheduleNewBookingController(ScheduleNewBookingActivity view) {
        this.view = view;
        initialize();
    }

    private void initialize() {
        view.setContentView(R.layout.activity_schedule_new_booking);

        view.getBackButton().setOnClickListener(v -> navigateToNavigationBar());

        view.getCancelButton().setOnClickListener(v -> cancelBooking());

        view.getDoneButton().setOnClickListener(v -> navigateToNavigationBar());

        // Retrieve booking information from the intent
        Intent intent = view.getIntent();
        if (intent != null) {
            String bookingTitle = intent.getStringExtra(view.getString(R.string.extra_booking_title));
            String bookingTime = intent.getStringExtra(view.getString(R.string.extra_booking_time));
            String bookingDate = intent.getStringExtra(view.getString(R.string.extra_booking_date));

            String bookingDetailText = view.getString(R.string.booking_detail_text, bookingTitle, bookingTime, bookingDate);
            view.getBookingDetailText().setText(bookingDetailText);

            foundBooking =  DatabaseHandlerSingleton.getInstance(view).getBookingByTimeAndDate(bookingTime, bookingDate);
        }
    }
    private void navigateToNavigationBar() {
        Intent intent = new Intent(view, NavigationBar.class);
        view.startActivity(intent);
    }

    private void cancelBooking() {
        if (foundBooking != null) {
            if ( DatabaseHandlerSingleton.getInstance(view).cancelBookingUser(foundBooking)) {
                Toast.makeText(getContext(), view.getResources().getString(R.string.booking_canceled_message), Toast.LENGTH_SHORT).show();
                navigateToNavigationBar();
            } else {
                Toast.makeText(getContext(), view.getResources().getString(R.string.error_cancelling_booking), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Context getContext() {
        return view.getApplicationContext();
    }
}
