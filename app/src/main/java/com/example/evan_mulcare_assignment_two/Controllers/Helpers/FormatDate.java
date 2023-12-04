package com.example.evan_mulcare_assignment_two.Controllers.Helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatDate {

    private FormatDate() {}

    public static String formatDate(Date date) {
        if (date == null) {
            return "N/A";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }
}
