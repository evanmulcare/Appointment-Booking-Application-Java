package com.example.evan_mulcare_assignment_two.Controllers.Helpers;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.evan_mulcare_assignment_two.R;

public class CalendarStyleHelper {

    private static final String SELECTED_STYLE_COLOR = "#000000";
    private static final String DEFAULT_STYLE_COLOR = "#E5E7EB";
    private static final String TEXT_COLOR_WHITE = "#FFFFFF";
    private static final String TEXT_COLOR_GRAY = "#666666";

    public static void setSelectedStyle(View view) {
        setViewBackgroundColor(view, SELECTED_STYLE_COLOR);
        setTextViewColors(view, TEXT_COLOR_WHITE);
    }

    public static void resetTextStyle(View view) {
        setViewBackgroundColor(view, DEFAULT_STYLE_COLOR);
        setTextViewColors(view, TEXT_COLOR_GRAY);
    }

    private static void setViewBackgroundColor(View view, String color) {
        if (view != null) {
            view.setBackgroundColor(Color.parseColor(color));
        }
    }

    private static void setTextViewColors(View view, String textColor) {
        TextView dayTextView = view.findViewById(R.id.dayTextView);
        TextView dateTextView = view.findViewById(R.id.dateTextView);
        TextView monthTextView = view.findViewById(R.id.monthTextView);

        if (dayTextView != null) {
            dayTextView.setTextColor(Color.parseColor(textColor));
        }
        if (dateTextView != null) {
            dateTextView.setTextColor(Color.parseColor(textColor));
        }
        if (monthTextView != null) {
            monthTextView.setTextColor(Color.parseColor(textColor));
        }
    }


}
