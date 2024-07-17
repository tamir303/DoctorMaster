package com.example.doctormaster.firebase;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctormaster.models.Appointment;
import com.example.doctormaster.models.Doctor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class Database {
    private static final DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    /*
     * DOCTORS
     */

    // Load all Doctors from DB
    public static void loadDoctors(AppCompatActivity currentActivity, FirestoreCallback<List<Doctor>> firestoreCallback) {
        List<Doctor> doctorList = new ArrayList<>();
        db.child("doctors").addListenerForSingleValueEvent(new ValueEventListener() {
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
    public static void addDoctor(Doctor doctor, FirestoreCallback firestoreCallback) {
        db.child("doctors").child(doctor.getUid()).setValue(doctor)
                .addOnSuccessListener(aVoid -> {
                    Log.d("FirebaseDatabase", "Doctor successfully added with ID: " + doctor.getUid());
                    firestoreCallback.onCallBack(true);
                })
                .addOnFailureListener(e -> {
                    Log.e("FirebaseDatabase", "Error adding doctor", e);
                    firestoreCallback.onCallBack(false);
                });
    }

    /*
     * APPOINTMENTS
     */

    // Add new Appointment to DB
    public static void addAppointmentToDB(@NonNull Appointment appointment) {
        db.child("appointments").push().setValue(appointment)
                .addOnSuccessListener(aVoid -> {
                    Log.d("FirebaseDatabase", "Appointment saved successfully.");
                })
                .addOnFailureListener(e -> {
                    Log.e("FirebaseDatabase", "Failed to save appointment.", e);
                });
    }

    // Load Appointment with ID
    public static void loadAppointmentByUid(String uid, FirestoreCallback<Appointment> firestoreCallback) {
        db.child("appointments").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Appointment appointment = dataSnapshot.getValue(Appointment.class);
                    firestoreCallback.onCallBack(appointment);
                } else {
                    Log.d("FirebaseDatabase", "No such document.");
                    firestoreCallback.onCallBack(null);  // No appointment found
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseDatabase", "Failed to load appointment.", databaseError.toException());
                firestoreCallback.onCallBack(null);  // Failed to retrieve appointment
            }
        });
    }

    // Update an existing appointment
    public static void updateAppointment(String uid, Map<String, Object> updates, FirestoreCallback<Boolean> firestoreCallback) {
        db.child("appointments").child(uid).updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                    Log.d("FirebaseDatabase", "Appointment updated successfully.");
                    firestoreCallback.onCallBack(true);  // Indicate success
                })
                .addOnFailureListener(e -> {
                    Log.e("FirebaseDatabase", "Error updating appointment.", e);
                    firestoreCallback.onCallBack(false);  // Indicate failure
                });
    }
}
