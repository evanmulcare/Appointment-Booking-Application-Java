package com.example.evan_mulcare_assignment_two.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.evan_mulcare_assignment_two.Controllers.BookingTabController;
import com.example.evan_mulcare_assignment_two.R;
public class BookingTabFragment extends Fragment {
    private BookingTabController controller;
    public BookingTabFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_tab, container, false);
        controller = new BookingTabController(view, getChildFragmentManager());

        return view;
    }
}
