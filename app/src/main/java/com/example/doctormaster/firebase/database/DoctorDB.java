package com.example.doctormaster.firebase.database;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctormaster.DoctorDetailActivity;
import com.example.doctormaster.adapter.DoctorAdapter;
import com.example.doctormaster.firebase.FirebaseOperations;
import com.example.doctormaster.firebase.FirestoreCallback;
import com.example.doctormaster.models.Doctor;
import com.example.doctormaster.utils.Constants;
import com.example.doctormaster.utils.Utils;
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

    // Get only doctors relevant to specific field and speciality
    public static void getDoctorsByFieldAndSpeciality(AppCompatActivity currentActivity,
                                                      FirestoreCallback<List<Doctor>> firestoreCallback,
                                                      String field,
                                                      String speciality)
    {
        DoctorDB.loadDoctors(currentActivity, new FirestoreCallback<List<Doctor>>() {
            @Override
            public void onCallBack(List<Doctor> doctorList) {
                if (doctorList.isEmpty()) {
                  Log.e("FirebaseDatabase", "No doctors found!");
                  firestoreCallback.onCallBack(Collections.emptyList());
                } else {
                    List<Doctor> filteredList = new ArrayList<>();
                    for (Doctor doctor : doctorList) {
                        if (doctor.getField().equals(field) && doctor.getSpecialties().contains(speciality))
                            filteredList.add(doctor);
                    }

                    firestoreCallback.onCallBack(filteredList);
                }
            }
        });
    }

    // Find doctor by UID
    public static void findDoctorByUid(AppCompatActivity currentActivity, String uid, FirestoreCallback<Doctor> firestoreCallback) {
        db.child(Constants.DOCTOR_DB).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Doctor doctor = dataSnapshot.getValue(Doctor.class);
                    firestoreCallback.onCallBack(doctor);
                } else {
                    Log.e("FirebaseDatabase", "Doctor with UID " + uid + " not found!");
                    firestoreCallback.onCallBack(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(currentActivity,
                        "Error getting doctor: " + databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
                firestoreCallback.onCallBack(null);
            }
        });
    }
}
