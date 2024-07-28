package com.example.doctormaster.utils;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public abstract class Date {

    // Sets calender date to today and prevent choosing a day earlier
    public static void setCalenderDefaultConfiguration(CalendarView calendarView) {
        try {
            long today = System.currentTimeMillis();
            calendarView.setDate(today, false, true);
            calendarView.setMinDate(today);
        } catch (IllegalArgumentException ex) {
            Log.e("DateUtil", Objects.requireNonNull(ex.getLocalizedMessage()));
        }
    }

    public static String convertLongToDateString(Long dateInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        java.util.Date date = new java.util.Date(dateInMillis);

        return sdf.format(date);
    }

    public static Long convertStringToDateLong(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            java.util.Date date = sdf.parse(dateString);
            assert date != null;
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L; // Handle the error case as needed
        }
    }

    @SuppressLint("DefaultLocale")
    public static String formatTime(String time) {
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        return String.format("%02d:%02d", hour, minute);
    }
}
