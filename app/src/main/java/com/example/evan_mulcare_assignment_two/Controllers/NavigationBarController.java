package com.example.evan_mulcare_assignment_two.Controllers;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.evan_mulcare_assignment_two.R;
import com.example.evan_mulcare_assignment_two.Views.BookingTabFragment;
import com.example.evan_mulcare_assignment_two.Views.HomeFragment;
import com.example.evan_mulcare_assignment_two.Views.NavigationBar;
import com.example.evan_mulcare_assignment_two.Views.NewBookingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationBarController {

    private final NavigationBar view;

    public NavigationBarController(NavigationBar view) {
        this.view = view;
        initialize();
    }

    private void initialize() {
        replaceFragment(new HomeFragment());

        view.getBottomNavigationView().setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.home) {
                    replaceFragment(new HomeFragment());
                } else if (itemId == R.id.upcoming) {
                    replaceFragment(new BookingTabFragment());
                } else if (itemId == R.id.newBooking) {
                    replaceFragment(new NewBookingFragment());
                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = view.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(view.getFrameLayout().getId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
