package com.example.doctormaster.firebase.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.doctormaster.firebase.FirebaseOperations;
import com.example.doctormaster.firebase.FirestoreCallback;
import com.example.doctormaster.models.Appointment;
import com.example.doctormaster.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public abstract class AppointmentDB {
    private static final DatabaseReference db = FirebaseOperations.getDatabaseRef();

    // Add new Appointment to DB
    public static void addAppointmentToDB(@NonNull Appointment appointment) {
        db.child(Constants.APPOINTMENT_DB).push().setValue(appointment)
                .addOnSuccessListener(aVoid -> {
                    Log.d("FirebaseDatabase", "Appointment saved successfully.");
                })
                .addOnFailureListener(e -> {
                    Log.e("FirebaseDatabase", "Failed to save appointment.", e);
                });
    }

    // Load Appointment with ID
    public static void loadAppointmentByUid(String uid, FirestoreCallback<Appointment> firestoreCallback) {
        db.child(Constants.APPOINTMENT_DB).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
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
        db.child(Constants.APPOINTMENT_DB).child(uid).updateChildren(updates)
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
