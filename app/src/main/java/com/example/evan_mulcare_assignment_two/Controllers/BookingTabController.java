package com.example.evan_mulcare_assignment_two.Controllers;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.evan_mulcare_assignment_two.R;
import com.example.evan_mulcare_assignment_two.Views.HistoryBookingFragment;
import com.example.evan_mulcare_assignment_two.Views.UpcomingBookingFragment;
import com.google.android.material.tabs.TabLayout;

public class BookingTabController {
    private final View view;
    private final FragmentManager fragmentManager;

    public BookingTabController(View view, FragmentManager fragmentManager) {
        this.view = view;
        this.fragmentManager = fragmentManager;
        initialize();
    }
    private void initialize() {
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager viewPager = view.findViewById(R.id.viewPager);

        UpcomingPagerAdapter adapter = new UpcomingPagerAdapter(fragmentManager, view.getContext());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private static class UpcomingPagerAdapter extends FragmentPagerAdapter {
        private final Context context;

        public UpcomingPagerAdapter(FragmentManager fm, Context context) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.context = context;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new UpcomingBookingFragment();
                case 1:
                    return new HistoryBookingFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return context.getString(R.string.status_upcoming);
                case 1:
                    return context.getString(R.string.status_history);
                default:
                    return null;
            }
        }
    }
}
