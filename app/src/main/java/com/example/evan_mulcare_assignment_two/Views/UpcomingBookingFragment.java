package com.example.evan_mulcare_assignment_two.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.evan_mulcare_assignment_two.Controllers.UpcomingBookingController;
import com.example.evan_mulcare_assignment_two.R;

public class UpcomingBookingFragment extends Fragment {
    private UpcomingBookingController controller;
    public UpcomingBookingFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_booking, container, false);
        controller = new UpcomingBookingController(view, requireContext());
        return view;
    }
}
