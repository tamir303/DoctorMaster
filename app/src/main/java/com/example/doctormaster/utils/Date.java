package com.example.doctormaster.utils;

import android.widget.CalendarView;

public abstract class Date {

    // Sets calender date to today and prevent choosing a day earlier
    public static void setCalenderDefaultConfiguration(CalendarView calendarView) {
        long today = System.currentTimeMillis();
        calendarView.setDate(today, false, true);
        calendarView.setMinDate(today);
    }
}
