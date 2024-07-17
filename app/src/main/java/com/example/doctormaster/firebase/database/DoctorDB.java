package com.example.doctormaster.firebase.database;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctormaster.firebase.FirebaseOperations;
import com.example.doctormaster.firebase.FirestoreCallback;
import com.example.doctormaster.models.Doctor;
import com.example.doctormaster.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class DoctorDB {
    private static final DatabaseReference db = FirebaseOperations.getDatabaseRef();

    // Load all Doctors from DB
    public static void loadDoctors(AppCompatActivity currentActivity, FirestoreCallback<List<Doctor>> firestoreCallback) {
        List<Doctor> doctorList = new ArrayList<>();
        db.child(Constants.DOCTOR_DB).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Doctor doctor = snapshot.getValue(Doctor.class);
                    doctorList.add(doctor);
                }
                firestoreCallback.onCallBack(doctorList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(currentActivity,
                        "Error getting doctors: " + databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
                firestoreCallback.onCallBack(Collections.emptyList());
            }
        });
    }

    // Add new Doctor to DB
    public static void addDoctor(Doctor doctor, FirestoreCallback<Boolean> firestoreCallback) {
        db.child(Constants.DOCTOR_DB).child(doctor.getUid()).setValue(doctor)
                .addOnSuccessListener(aVoid -> {
                    Log.d("FirebaseDatabase", "Doctor successfully added with ID: " + doctor.getUid());
                    firestoreCallback.onCallBack(true);
                })
                .addOnFailureListener(e -> {
                    Log.e("FirebaseDatabase", "Error adding doctor", e);
                    firestoreCallback.onCallBack(false);
                });
    }
}
