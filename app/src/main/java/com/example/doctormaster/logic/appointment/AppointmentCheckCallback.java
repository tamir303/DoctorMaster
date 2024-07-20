package com.example.doctormaster.logic.appointment;

import com.example.doctormaster.models.Appointment;

public interface AppointmentCheckCallback {
    void onResult(boolean actionResult, Appointment appointment);
}
