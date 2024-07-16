package com.example.doctormaster.firebase;

import com.example.doctormaster.models.Doctor;

import java.util.List;

public interface FirestoreCallback<T> {
    void onCallBack(T result);
}

