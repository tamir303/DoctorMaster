package com.example.doctormaster.utils;

import android.annotation.SuppressLint;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public abstract class Date {

    // Sets calender date to today and prevent choosing a day earlier
    public static void setCalenderDefaultConfiguration(CalendarView calendarView) {
        long today = System.currentTimeMillis();
        calendarView.setDate(today, false, true);
        calendarView.setMinDate(today);
    }

    public static String convertLongToDateString(Long dateInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        java.util.Date date = new java.util.Date(dateInMillis);

        return sdf.format(date);
    }

    @SuppressLint("DefaultLocale")
    public static String formatTime(String time) {
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        return String.format("%02d:%02d", hour, minute);
    }
}
