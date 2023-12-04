package com.example.evan_mulcare_assignment_two.Models;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerSingleton extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "appointment_database";
    private static final String TABLE_USERS = "userdetails";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private Context appContext;
    private static final String TABLE_BOOKINGS = "bookings";
    private static final String COLUMN_BOOKING_ID = "booking_id";
    private static final String COLUMN_BOOKING_TITLE = "booking_title";
    private static final String COLUMN_BOOKING_IMAGE = "booking_image";
    private static final String COLUMN_BOOKING_USER = "booking_user";
    private static final String COLUMN_BOOKING_TIME = "booking_time";
    private static final String COLUMN_BOOKING_DATE = "booking_date";

    private static final String CREATE_USER_TABLE_QUERY =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT)";

    private static final String CREATE_BOOKING_TABLE_QUERY =
            "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                    COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_BOOKING_TITLE + " TEXT, " +
                    COLUMN_BOOKING_IMAGE + " INTEGER, " +
                    COLUMN_BOOKING_USER + " TEXT, " +
                    COLUMN_BOOKING_TIME + " TEXT, " +
                    COLUMN_BOOKING_DATE + " TEXT)";

    private static DatabaseHandlerSingleton instance;

    // Private constructor to prevent instantiation outside the class
    private DatabaseHandlerSingleton(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        appContext = context.getApplicationContext();

    }

    // Public method to get the singleton instance
    public static synchronized DatabaseHandlerSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHandlerSingleton(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE_QUERY);
        db.execSQL(CREATE_BOOKING_TABLE_QUERY);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);

        // Recreate the tables
        onCreate(db);
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        // Note: user.getUserId() is set to 0 as it will be auto-incremented
        cValues.put(COLUMN_USERNAME, user.getUsername());
        cValues.put(COLUMN_PASSWORD, user.getPassword());

        long result = db.insert(TABLE_USERS, null, cValues);
        db.close();

        // Check if the insertion was successful
        return result != -1;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String[] columns = {COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD};
        String selection = COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int userIdIndex = cursor.getColumnIndex(COLUMN_ID);

            // Check if the COLUMN_ID exists in the cursor
            if (userIdIndex != -1) {
                int userId = cursor.getInt(userIdIndex);
                @SuppressLint("Range") String fetchedUsername = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                @SuppressLint("Range") String fetchedPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

                // Create a User object
                user = new User(userId, fetchedUsername, fetchedPassword);
            } else {
                // Handle the case when COLUMN_ID is not found in the cursor
                Log.e("DatabaseHandler", "COLUMN_ID not found in the cursor");
            }

            // Close the cursor
            cursor.close();
        }

        // Close the database
        db.close();

        return user;
    }

    public boolean addBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();

        cValues.put(COLUMN_BOOKING_TITLE, booking.getTitle());
        cValues.put(COLUMN_BOOKING_IMAGE, booking.getImageResourceId());
        cValues.put(COLUMN_BOOKING_USER, booking.getUserName());
        cValues.put(COLUMN_BOOKING_TIME, booking.getBookingTime());
        cValues.put(COLUMN_BOOKING_DATE, booking.getBookingDate());

        Log.d("Booking Time", booking.getBookingTime());
        Log.d("Booking Date", booking.getBookingDate());

        long result = db.insert(TABLE_BOOKINGS, null, cValues);
        db.close();

        return result != -1;
    }

    public boolean updateBookingUser(Booking booking, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();

        cValues.put(COLUMN_BOOKING_USER, username);

        String whereClause = COLUMN_BOOKING_DATE + " = ? AND " + COLUMN_BOOKING_TIME + " = ?";
        String[] whereArgs = {booking.getBookingDate(), booking.getBookingTime()};

        int rowsUpdated = db.update(TABLE_BOOKINGS, cValues, whereClause, whereArgs);
        db.close();

        return rowsUpdated > 0;
    }

    public boolean cancelBookingUser(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();

        cValues.putNull(COLUMN_BOOKING_USER);

        String whereClause = COLUMN_BOOKING_DATE + " = ? AND " + COLUMN_BOOKING_TIME + " = ?";
        String[] whereArgs = {booking.getBookingDate(), booking.getBookingTime()};

        int rowsUpdated = db.update(TABLE_BOOKINGS, cValues, whereClause, whereArgs);
        db.close();

        return rowsUpdated > 0;
    }

    public Booking getBookingByTimeAndDate(String time, String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                COLUMN_BOOKING_ID,
                COLUMN_BOOKING_TITLE,
                COLUMN_BOOKING_IMAGE,
                COLUMN_BOOKING_USER,
                COLUMN_BOOKING_TIME,
                COLUMN_BOOKING_DATE
        };

        String selection = COLUMN_BOOKING_TIME + " = ? AND " + COLUMN_BOOKING_DATE + " = ?";
        String[] selectionArgs = {time, date};

        Cursor cursor = db.query(TABLE_BOOKINGS, columns, selection, selectionArgs, null, null, null);

        Booking foundBooking = null;

        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve data from the cursor
            @SuppressLint("Range") int bookingId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_ID));
            @SuppressLint("Range")  String title = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_TITLE));
            @SuppressLint("Range") int imageResourceId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_IMAGE));
            @SuppressLint("Range") String userName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_USER));
            @SuppressLint("Range")  String bookingTime = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_TIME));
            @SuppressLint("Range") String bookingDate = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_DATE));

            // Create a Booking object
            foundBooking = new Booking(title, imageResourceId, userName, bookingTime, bookingDate);
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return foundBooking;
    }


    // Update the method signature to accept a date parameter
    public List<Booking> getBookingsForDay(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Booking> bookings = new ArrayList<>();

        String[] columns = {
                COLUMN_BOOKING_ID,
                COLUMN_BOOKING_TITLE,
                COLUMN_BOOKING_IMAGE,
                COLUMN_BOOKING_USER,
                COLUMN_BOOKING_TIME,
                COLUMN_BOOKING_DATE
        };

        String selection = COLUMN_BOOKING_DATE + " = ?";
        String[] selectionArgs = {date};

        Cursor cursor = db.query(TABLE_BOOKINGS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Retrieve data from the cursor
                @SuppressLint("Range") int bookingId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_TITLE));
                @SuppressLint("Range") int imageResourceId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_IMAGE));
                @SuppressLint("Range") String userName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_USER));
                @SuppressLint("Range") String bookingTime = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_TIME));
                @SuppressLint("Range") String bookingDate = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_DATE));

                // Create a Booking object
                Booking booking = new Booking(title, imageResourceId, userName, bookingTime,bookingDate);
                bookings.add(booking);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();

        return bookings;
    }

    public List<Booking> getBookingsForUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Booking> bookings = new ArrayList<>();

        String[] columns = {
                COLUMN_BOOKING_ID,
                COLUMN_BOOKING_TITLE,
                COLUMN_BOOKING_IMAGE,
                COLUMN_BOOKING_USER,
                COLUMN_BOOKING_TIME,
                COLUMN_BOOKING_DATE
        };

        String selection = COLUMN_BOOKING_USER + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_BOOKINGS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Retrieve data from the cursor
                @SuppressLint("Range") int bookingId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_TITLE));
                @SuppressLint("Range") int imageResourceId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOKING_IMAGE));
                @SuppressLint("Range") String userName = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_USER));
                @SuppressLint("Range") String bookingTime = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_TIME));
                @SuppressLint("Range") String bookingDate = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_DATE));

                // Create a Booking object
                Booking booking = new Booking(title, imageResourceId, userName, bookingTime, bookingDate);
                bookings.add(booking);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();

        return bookings;
    }
}

