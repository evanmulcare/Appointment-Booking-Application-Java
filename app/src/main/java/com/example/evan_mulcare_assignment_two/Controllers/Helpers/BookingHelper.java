package com.example.evan_mulcare_assignment_two.Controllers.Helpers;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.evan_mulcare_assignment_two.Controllers.BookingTitleGenerator;
import com.example.evan_mulcare_assignment_two.Models.Booking;
import com.example.evan_mulcare_assignment_two.Models.DatabaseHandlerSingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingHelper {

    public static boolean isToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    public static List<Booking> getBookingsForDay(String selectedDateFormatted, View view) {
        List<Booking> bookingsForDay =  DatabaseHandlerSingleton.getInstance(view.getContext()).getBookingsForDay(selectedDateFormatted);
        return bookingsForDay;
    }

    public static List<Booking> filterBookingsForToday(List<Booking> bookings) {
        SimpleDateFormat hourFormat = new SimpleDateFormat("h:mma", Locale.getDefault());
        String currentHour = hourFormat.format(new Date());

        List<Booking> filteredBookings = new ArrayList<>();

        for (Booking booking : bookings) {
            String bookingHour = booking.getBookingTime();

            if (compareBookingHours(bookingHour, currentHour) >= 0) {
                filteredBookings.add(booking);
            }
        }

        return filteredBookings;
    }

    public static void generateAndRetrieveBookings(String selectedDateFormatted, View view, Fragment fragment) {
        generateBookingsForDay(selectedDateFormatted, fragment);
        List<Booking> bookingsForDay =  DatabaseHandlerSingleton.getInstance(view.getContext()).getBookingsForDay(selectedDateFormatted);
    }

    public static int compareBookingHours(String bookingHour, String currentHour) {
        SimpleDateFormat hourFormat = new SimpleDateFormat("h:mma", Locale.getDefault());

        try {
            Date bookingTime = hourFormat.parse(bookingHour);
            Date currentTime = hourFormat.parse(currentHour);

            return bookingTime.compareTo(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static void generateBookingsForDay(String selectedDateFormatted, Fragment fragment) {
        BookingTitleGenerator bookingTitleGenerator = new BookingTitleGenerator();
        bookingTitleGenerator.createBookingListCurrentDay(fragment.getContext(), selectedDateFormatted);
    }
}
