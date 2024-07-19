package com.example.doctormaster.logic;

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
        AppointmentDB.loadAppointmentByUid(appointment.getUUID(), new FirestoreCallback<Appointment>() {
            @Override
            public void onCallBack(Appointment result) {
                if (result != null) {
                    Log.d("AppointmentServiceImpl", "Appointment Already Exist!");

                    updateAppointment(appointment, new AppointmentCheckCallback() {
                        @Override
                        public void onResult(boolean actionResult, Appointment appointment) {
                            appointmentCheckCallback.onResult(actionResult, appointment);
                        }
                    });
                } else {
                    Log.d("AppointmentServiceImpl", "Creating new Appointment!");

                    // Insert new appointment to DB
                    createNewAppointment(appointment, new AppointmentCheckCallback() {
                        @Override
                        public void onResult(boolean actionResults, Appointment appointment) {
                            // Return status of async action
                            appointmentCheckCallback.onResult(actionResults, appointment);
                        }
                    });
                }
            }
        });
    }

    private void createNewAppointment(Appointment appointment, AppointmentCheckCallback appointmentCheckCallback) {
        AppointmentDB.addAppointmentToDB(appointment, new FirestoreCallback<Boolean>() {
            @Override
            public void onCallBack(Boolean result) {
                if (result)
                    Log.d("AppointmentServiceImpl", "Appointment Creation Success!");
                else
                    Log.e("AppointmentServiceImpl", "Appointment Creation Failure!");

                appointmentCheckCallback.onResult(result, appointment);
            }
        });
    }

    private void updateAppointment(Appointment appointment, AppointmentCheckCallback appointmentCheckCallback) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("time", appointment.getTime());
        AppointmentDB.updateAppointment(appointment.getUUID(), updates, new FirestoreCallback<Boolean>() {
            @Override
            public void onCallBack(Boolean result) {
                if (result)
                    Log.d("AppointmentServiceImpl", "Appointment Update Success!");
                else
                    Log.e("AppointmentServiceImpl", "Appointment Update Failure!");

                appointmentCheckCallback.onResult(result, appointment);
            }
        });
    }
}
