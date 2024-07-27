package com.example.doctormaster.utils;

import static com.example.doctormaster.utils.Date.convertLongToDateString;

import com.example.doctormaster.firebase.FirestoreCallback;
import com.example.doctormaster.firebase.database.AppointmentDB;
import com.example.doctormaster.models.Appointment;
import com.example.doctormaster.models.Doctor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AppointmentTimesUtils {
    public static void calculateAvailableTimes(int startHour, int finishHour, int breakHour, int breakLength, String doctorName, long date, FirestoreCallback<List<String>> availableTimes) {
        notAvailableTimes(doctorName, date, new FirestoreCallback<List<String>>() {
            @Override
            public void onCallBack(List<String> notAvailableAppointments) {
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
                    String currentTime = Date.formatTime(calendar.getTime().getHours() + ":" + calendar.getTime().getMinutes());
                    boolean isTimeAvailable = !notAvailableAppointments.contains(currentTime);

                    if ((calendar.before(breakStartCalendar) || calendar.after(breakEndCalendar)) && isTimeAvailable) {
                        times.add(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
                    }

                    calendar.add(Calendar.MINUTE, 30);
                }

                availableTimes.onCallBack(times);
            }
        });
    }

    private static void notAvailableTimes(String doctorName, long date, FirestoreCallback<List<String>> notAvailableAppointments) {
        AppointmentDB.loadAppointmentsByDoctor(doctorName, new FirestoreCallback<List<Appointment>>() {
            @Override
            public void onCallBack(List<Appointment> result) {
                List<String> times = new ArrayList<>();

                if (result != null) {
                    result.forEach(appointment -> {
                        if (appointment.getDate().equals(convertLongToDateString(date)))
                            times.add(appointment.getTime());
                    });
                }

                notAvailableAppointments.onCallBack(times);
            }
        });
    }
}
