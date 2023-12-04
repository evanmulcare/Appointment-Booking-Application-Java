package com.example.evan_mulcare_assignment_two.Views;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.evan_mulcare_assignment_two.Controllers.HistoryBookingController;
import com.example.evan_mulcare_assignment_two.R;

public class HistoryBookingFragment extends Fragment {
    private HistoryBookingController controller;
    public HistoryBookingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_booking, container, false);
        controller = new HistoryBookingController(view, requireContext());
        return view;
    }
}