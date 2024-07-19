package com.example.doctormaster.logic;

import com.example.doctormaster.models.Appointment;

public interface AppointmentService {
    void processAppointmentRequest(String userUid, String doctorName, String location, String time, String date, AppointmentCheckCallback appointmentCheckCallback);
}
