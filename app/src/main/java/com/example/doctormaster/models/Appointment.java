package com.example.doctormaster.models;

import androidx.annotation.NonNull;

public class Appointment {
    private String uid;
    private String doctor;
    private String date;
    private String time;
    private String location;

    public Appointment() {
    }

    public Appointment(String uid, String doctor, String date, String time, String location) {
        this.uid = uid;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.location = location;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(
                "An appointment was made for \n %s,\nDate %s at %s in %s.",
                doctor, date, time, location
        );
    }

    public String getUUID() {
        return (uid+doctor+date).replaceAll("[.\\s]", "");
    }
}
