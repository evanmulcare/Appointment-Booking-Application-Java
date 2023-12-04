package com.example.evan_mulcare_assignment_two.Views;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.evan_mulcare_assignment_two.Controllers.NavigationBarController;
import com.example.evan_mulcare_assignment_two.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationBar extends AppCompatActivity {
    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationBarController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);

        frameLayout = findViewById(R.id.frame_layout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        controller = new NavigationBarController(this);
    }
    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }
    public FrameLayout getFrameLayout() {
        return frameLayout;
    }
}
