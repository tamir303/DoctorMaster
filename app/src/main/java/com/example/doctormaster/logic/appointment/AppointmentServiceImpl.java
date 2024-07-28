package com.example.doctormaster.logic.appointment;

import android.util.Log;

import com.example.doctormaster.firebase.FirestoreCallback;
import com.example.doctormaster.firebase.database.AppointmentDB;
import com.example.doctormaster.models.Appointment;

import java.util.HashMap;
import java.util.Map;

public class AppointmentServiceImpl implements AppointmentService {
    public Appointment currentAppointment;

    @Override
    public void processAppointmentRequest(String userUid, String doctorName, String location, String time, String date, AppointmentCheckCallback appointmentCheckCallback) {
        Appointment appointment = new Appointment(userUid, doctorName, date, time, location);
        AppointmentDB.loadAppointmentByUid(appointment.getUUID(), result -> {
            if (result != null) {
                Log.d("AppointmentServiceImpl", "Appointment Already Exist!");

                updateAppointment(appointment, (actionResult, appointment12) -> appointmentCheckCallback.onResult(actionResult, appointment12));
            } else {
                Log.d("AppointmentServiceImpl", "Creating new Appointment!");

                // Insert new appointment to DB
                createNewAppointment(appointment, (actionResults, appointment1) -> {
                    // Return status of async action
                    appointmentCheckCallback.onResult(actionResults, appointment1);
                });
            }
        });
    }

    private void createNewAppointment(Appointment appointment, AppointmentCheckCallback appointmentCheckCallback) {
        AppointmentDB.addAppointmentToDB(appointment, result -> {
            if (result)
                Log.d("AppointmentServiceImpl", "Appointment Creation Success!");
            else
                Log.e("AppointmentServiceImpl", "Appointment Creation Failure!");

            appointmentCheckCallback.onResult(result, appointment);
        });
    }

    private void updateAppointment(Appointment appointment, AppointmentCheckCallback appointmentCheckCallback) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("time", appointment.getTime());
        AppointmentDB.updateAppointment(appointment.getUUID(), updates, result -> {
            if (result)
                Log.d("AppointmentServiceImpl", "Appointment Update Success!");
            else
                Log.e("AppointmentServiceImpl", "Appointment Update Failure!");

            appointmentCheckCallback.onResult(result, appointment);
        });
    }
}
