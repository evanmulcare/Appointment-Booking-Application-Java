package com.example.evan_mulcare_assignment_two.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evan_mulcare_assignment_two.Controllers.ScheduleNewBookingController;
import com.example.evan_mulcare_assignment_two.R;

public class ScheduleNewBookingActivity extends AppCompatActivity {
    private ImageView backButton;
    private Button doneButton;
    private Button cancelButton;
    private TextView bookingDetailText;
    private ScheduleNewBookingController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new ScheduleNewBookingController(this);
    }
    public ImageView getBackButton() {
        if (backButton == null) {
            backButton = findViewById(R.id.BackButton);
        }
        return backButton;
    }

    public Button getDoneButton() {
        if (doneButton == null) {
            doneButton = findViewById(R.id.doneButton);
        }
        return doneButton;
    }

    public Button getCancelButton() {
        if (cancelButton == null) {
            cancelButton = findViewById(R.id.cancelButton);
        }
        return cancelButton;
    }

    public TextView getBookingDetailText() {
        if (bookingDetailText == null) {
            bookingDetailText = findViewById(R.id.bookingDetailText);
        }
        return bookingDetailText;
    }
}
