package com.example.evan_mulcare_assignment_two.Controllers;

import android.content.Context;

import com.example.evan_mulcare_assignment_two.Models.Booking;
import com.example.evan_mulcare_assignment_two.Models.DatabaseHandlerSingleton;
import com.example.evan_mulcare_assignment_two.Models.TitleOption;
import com.example.evan_mulcare_assignment_two.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class BookingTitleGenerator {
    private static final String[] PEOPLE_OPTIONS = TitleOption.getPeopleOptions();
    private static final String[] ANIMAL_OPTIONS = TitleOption.getAnimalOptions();
    private static final String[] SEE_OPTIONS = TitleOption.getSeeOptions();
    private static final String[] ABOUT_OPTIONS = TitleOption.getAboutOptions();
    private static final int HOURS_IN_A_DAY = 24;

    public static String generateRandomTitle() {
        String seeVerb = getRandomOption(SEE_OPTIONS);
        String aboutPreposition = getRandomOption(ABOUT_OPTIONS);
        String person = getRandomOption(PEOPLE_OPTIONS);
        String animal = getRandomOption(ANIMAL_OPTIONS);

        return seeVerb + " a " + person + " " + aboutPreposition + animal;
    }

    private static String getRandomOption(String[] options) {
        int randomIndex = new Random().nextInt(options.length);
        return options[randomIndex];
    }

    public List<Booking> createBookingListCurrentDay(Context context, String date) {
        List<Booking> bookingList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        try {
            // Set the date to the desired day
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date selectedDate = dateFormat.parse(date);
            calendar.setTime(selectedDate);

            // Set the time to the beginning of the day
            calendar.set(Calendar.HOUR_OF_DAY, 9); // Start at 9:00 am
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            // Create bookings for each remaining hour in the day up to 8:00 pm
            for (int hour = 9; hour <= 20; hour++) {
                if (hour >= 9 && hour <= 20) {
                    Booking booking = createBookingForHour(calendar, date);
                    bookingList.add(booking);

                    // Increment the hour
                    calendar.add(Calendar.HOUR_OF_DAY, 1);

                    // Save the booking to the database
                    saveBookingToDatabase(booking, context);
                }
            }
        } catch (ParseException e) {
            // Handle parsing exception (log or throw a more descriptive exception)
            e.printStackTrace();
        }

        return bookingList;
    }


    private Booking createBookingForHour(Calendar calendar, String date) {
        String randomTitle = generateRandomTitle();
        int imageResource = getImageForResource(randomTitle);

        // Format the time as "1:00pm", "2:00pm", ...
        SimpleDateFormat sdf = new SimpleDateFormat("h:00a", Locale.getDefault());
        String formattedTime = sdf.format(calendar.getTime());

        return new Booking(
                randomTitle,
                imageResource,
                null,
                formattedTime,
                date
        );
    }

    private void saveBookingToDatabase(Booking booking, Context context) {
        DatabaseHandlerSingleton.getInstance(context).addBooking(booking);
    }


    private static int getImageForResource(String title) {
        Map<String, Integer> animalImageMap = TitleOption.getAnimalImageMap();

        if (animalImageMap != null) {
            for (String animal : ANIMAL_OPTIONS) {
                if (title.contains(animal) && animalImageMap.containsKey(animal)) {
                    return animalImageMap.get(animal);
                }
            }
        }

        return R.drawable.unknown;
    }

}
