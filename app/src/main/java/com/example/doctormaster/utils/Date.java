package com.example.doctormaster.utils;

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
}
