package com.example.doctormaster.models;

import java.util.List;

public class Doctor {
    private String uid;
    private String name;
    private String location;
    private String field;
    private String description;
    private List<String> specialties;
    private Integer experience;
    private Integer rating;
    private Integer startHour;
    private Integer finishHour;
    private Integer breakHour;
    private Integer breakLength;

    public Doctor() {
    }

    public Doctor(String uid, String name, String location, String field, String description, List<String> specialties, Integer experience, Integer rating, Integer startHour, Integer finishHour, Integer breakHour, Integer breakLength) {
        this.uid = uid;
        this.name = name;
        this.location = location;
        this.field = field;
        this.description = description;
        this.specialties = specialties;
        this.experience = experience;
        this.rating = rating;
        this.startHour = startHour;
        this.finishHour = finishHour;
        this.breakHour = breakHour;
        this.breakLength = breakLength;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<String> specialties) {
        this.specialties = specialties;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getFinishHour() {
        return finishHour;
    }

    public void setFinishHour(Integer finishHour) {
        this.finishHour = finishHour;
    }

    public Integer getBreakHour() {
        return breakHour;
    }

    public void setBreakHour(Integer breakHour) {
        this.breakHour = breakHour;
    }

    public Integer getBreakLength() {
        return breakLength;
    }

    public void setBreakLength(Integer breakLength) {
        this.breakLength = breakLength;
    }
}
