package com.example.doctormaster.models;

import java.util.List;

public class MedicalFieldDetails {
    String name;
    List<String> details;

    public MedicalFieldDetails() {
    }

    public MedicalFieldDetails(String name, List<String> details) {
        this.name = name;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
