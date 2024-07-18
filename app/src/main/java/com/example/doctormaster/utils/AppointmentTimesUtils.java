package com.example.doctormaster.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AppointmentTimesUtils {
    public static List<String> calculateAvailableTimes(int startHour, int finishHour, int breakHour, int breakLength) {
        List<String> times = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, startHour);
        calendar.set(Calendar.MINUTE, 0);

        Calendar finishCalendar = Calendar.getInstance();
        finishCalendar.set(Calendar.HOUR_OF_DAY, finishHour);
        finishCalendar.set(Calendar.MINUTE, 0);

        Calendar breakStartCalendar = Calendar.getInstance();
        breakStartCalendar.set(Calendar.HOUR_OF_DAY, breakHour);
        breakStartCalendar.set(Calendar.MINUTE, 0);

        Calendar breakEndCalendar = (Calendar) breakStartCalendar.clone();
        breakEndCalendar.add(Calendar.MINUTE, breakLength);

        while (calendar.before(finishCalendar)) {
            if (calendar.before(breakStartCalendar) || calendar.after(breakEndCalendar)) {
                times.add(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
            }
            calendar.add(Calendar.MINUTE, 30);
        }

        return times;
    }
}
