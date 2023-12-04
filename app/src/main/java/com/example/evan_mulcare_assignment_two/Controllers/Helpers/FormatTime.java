package com.example.evan_mulcare_assignment_two.Controllers.Helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatTime {

    // Private constructor to prevent instantiation
    private FormatTime() {
    }

    public static String formatTime(Date date) {
        if (date == null) {
            return "N/A";
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma", Locale.getDefault());
        return timeFormat.format(date);
    }
}
