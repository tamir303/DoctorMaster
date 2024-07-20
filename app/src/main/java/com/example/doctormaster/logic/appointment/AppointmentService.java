package com.example.doctormaster.logic.appointment;

public interface AppointmentService {
    void processAppointmentRequest(String userUid, String doctorName, String location, String time, String date, AppointmentCheckCallback appointmentCheckCallback);
}
