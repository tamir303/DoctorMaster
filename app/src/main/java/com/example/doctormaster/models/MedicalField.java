package com.example.doctormaster.models;

public class MedicalField {
    String name;
    String icon;

    public MedicalField() {
    }

    public MedicalField(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
