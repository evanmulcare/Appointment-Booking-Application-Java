package com.example.evan_mulcare_assignment_two.Views;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.evan_mulcare_assignment_two.Controllers.NewBookingController;

public class NewBookingFragment extends Fragment {
    private NewBookingController controller;
    public NewBookingFragment() {
        controller = new NewBookingController(this);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return controller.onCreateView(inflater, container, savedInstanceState);
    }

}