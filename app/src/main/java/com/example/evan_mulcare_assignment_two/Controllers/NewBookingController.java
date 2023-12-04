package com.example.evan_mulcare_assignment_two.Controllers;

import static com.example.evan_mulcare_assignment_two.Controllers.Helpers.BookingHelper.filterBookingsForToday;
import static com.example.evan_mulcare_assignment_two.Controllers.Helpers.BookingHelper.generateAndRetrieveBookings;
import static com.example.evan_mulcare_assignment_two.Controllers.Helpers.BookingHelper.getBookingsForDay;
import static com.example.evan_mulcare_assignment_two.Controllers.Helpers.BookingHelper.isToday;
import static com.example.evan_mulcare_assignment_two.Controllers.Helpers.FormatDate.formatDate;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evan_mulcare_assignment_two.Controllers.Helpers.CalendarStyleHelper;
import com.example.evan_mulcare_assignment_two.Models.Booking;
import com.example.evan_mulcare_assignment_two.Models.DatabaseHandlerSingleton;
import com.example.evan_mulcare_assignment_two.R;
import com.example.evan_mulcare_assignment_two.Views.NewBookingFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewBookingController {
    private final NewBookingFragment fragment;
    private static final int MAX_WEEK_DAYS = 10;
    private String selectedDateFormatted;


    public NewBookingController(NewBookingFragment fragment) {
        this.fragment = fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_booking, container, false);
        DatabaseHandlerSingleton.getInstance(view.getContext());
        initializeViews(view);
        return view;
    }

    public void updateVerticalRecyclerView(List<Booking> bookingsForDay) {
        BookingVerticalAdapter bookingAdapter = new BookingVerticalAdapter(bookingsForDay, fragment.getContext());
        RecyclerView verticalRecyclerView = fragment.getView().findViewById(R.id.verticalRecyclerView);
        verticalRecyclerView.setAdapter(bookingAdapter);
    }

    private void initializeViews(View view) {
        // Initialize the RecyclerView of available bookings
        RecyclerView verticalRecyclerView = view.findViewById(R.id.verticalRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragment.getContext());
        verticalRecyclerView.setLayoutManager(layoutManager);

        //generate horizontal tiles for the current week
        initializeHorizontalCalendar(view);

        View firstItem = getHorizontalLayoutChildAt(view, 0);

        //click first item in horizontalCalendar
        firstItem.post(() -> firstItem.performClick());

        setHorizontalItemClickListener(view);
    }

    private void initializeHorizontalCalendar(View view) {
        LinearLayout horizontalLayout = view.findViewById(R.id.horizontalLayout);
        Calendar calendar = Calendar.getInstance();

        List<Date> weekDates = getWeekDates(calendar.getTime());
        List<Booking> bookingList = new ArrayList<>();

        BookingVerticalAdapter bookingAdapter = new BookingVerticalAdapter(bookingList, view.getContext());
        RecyclerView verticalRecyclerView = view.findViewById(R.id.verticalRecyclerView);
        verticalRecyclerView.setAdapter(bookingAdapter);

        for (Date date : weekDates) {
            View horizontalCalendarItem = addHorizontalTileItem(calendar,date);
            horizontalLayout.addView(horizontalCalendarItem);
        }
    }


    private View addHorizontalTileItem(Calendar calendar, Date date){
        View horizontalCalendarItem = LayoutInflater.from(fragment.getContext()).inflate(R.layout.horizontal_calendar_item_layout, null);

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
        TextView dayTextView = horizontalCalendarItem.findViewById(R.id.dayTextView);
        TextView dateTextView = horizontalCalendarItem.findViewById(R.id.dateTextView);
        TextView monthTextView = horizontalCalendarItem.findViewById(R.id.monthTextView);

        calendar.setTime(date);
        dayTextView.setText(dayFormat.format(date));
        dateTextView.setText(dateFormat.format(date));
        monthTextView.setText(new SimpleDateFormat("MMM", Locale.getDefault()).format(date));
        selectedDateFormatted = formatDate(date);
        return horizontalCalendarItem;
    }


    //when  you click an item in horizontally layout
    private View getHorizontalLayoutChildAt(View view, int index) {
        LinearLayout horizontalLayout = view.findViewById(R.id.horizontalLayout);
        return horizontalLayout.getChildAt(index);
    }

    private void resetHorizontalCalendarStyles(View view) {
        LinearLayout horizontalLayout = view.findViewById(R.id.horizontalLayout);

        for (int j = 0; j < horizontalLayout.getChildCount(); j++) {
            View item = getHorizontalLayoutChildAt(view, j);
            CalendarStyleHelper.resetTextStyle(item);
        }
    }

    private void setHorizontalItemClickListener(View view) {
        LinearLayout horizontalLayout = view.findViewById(R.id.horizontalLayout);
        List<Date> weekDates = getWeekDates(Calendar.getInstance().getTime());

        for (int i = 0; i < horizontalLayout.getChildCount(); i++) {
            View horizontalCalendarItem = getHorizontalLayoutChildAt(horizontalLayout, i);
            final Date date = weekDates.get(i);

            horizontalCalendarItem.setOnClickListener(v -> {
                handleHorizontalCalendarItemClick(v, view, date);
            });
        }
    }

    private void handleHorizontalCalendarItemClick(View v, View view, Date date) {
        resetHorizontalCalendarStyles(view);
        CalendarStyleHelper.setSelectedStyle(v);

        String selectedDateFormatted = formatDate(date);
        boolean isToday = isToday(date);

        List<Booking> bookingsForDay = getBookingsForDay(selectedDateFormatted,view);

        // Filter bookings for today after the current hour
        if (isToday) {
            bookingsForDay = filterBookingsForToday(bookingsForDay);
        }

        // Generate bookings if there are none
        if (bookingsForDay == null || bookingsForDay.isEmpty()) {
            generateAndRetrieveBookings(selectedDateFormatted,view, fragment);
        }

        updateVerticalRecyclerView(bookingsForDay);
    }

    //get the amount of days for the calendar
    private List<Date> getWeekDates(Date currentDate) {
        List<Date> weekDates = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.setFirstDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int daysUntilStartOfWeek = (calendar.getFirstDayOfWeek() - dayOfWeek + 7) % 7;
        calendar.add(Calendar.DAY_OF_YEAR, daysUntilStartOfWeek);

        for (int i = 0; i <= MAX_WEEK_DAYS; i++) {
            weekDates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return weekDates;
    }

}

