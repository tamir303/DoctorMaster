package com.example.doctormaster.firebase;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctormaster.models.Appointment;
import com.example.doctormaster.models.Doctor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class Database {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /*
     * DOCTORS
     */

    // Load all Doctors from DB
    public static void loadDoctors(AppCompatActivity currentActivity, FirestoreCallback<List<Doctor>> firestoreCallback) {
        List<Doctor> doctorList = new ArrayList<>();
        db.collection("doctors").get()
                .addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
               for (QueryDocumentSnapshot document : task.getResult()) {
                   doctorList.add(document.toObject(Doctor.class));
               }

               firestoreCallback.onCallBack(doctorList);
           } else {
               Toast.makeText(currentActivity,
                       "Error getting doctors: " + task.getException().getMessage(),
                       Toast.LENGTH_SHORT).show();

               firestoreCallback.onCallBack(Collections.emptyList());
           }
        });
    }

    // Add new Doctor to DB
    public static void addDoctor(Doctor doctor, FirestoreCallback firestoreCallback) {
        DocumentReference doctorRef = db.collection("doctors").document(doctor.getUid());

        doctorRef.set(doctor)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "Doctor successfully added with ID: " + doctor.getUid());
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error adding doctor", e);
                });
    }

    /*
    * APPOINTMENTS
    */

    // Add new Appointment to DB
    public static void addAppointmentToDB(@NonNull Appointment appointment) {
        CollectionReference appointmentRef = db.collection("appointments");

        appointmentRef.add(appointment)
                .addOnSuccessListener(aVoid-> {
                    Log.d("Firestore", "Appointment saved successfully.");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Failed to save appointment.", e);
                });
    }

    // Load Appointment with ID
    public static void loadAppointmentByUid(String uid, FirestoreCallback<Appointment> firestoreCallback) {
        DocumentReference appointmentRef = db.collection("appointments").document(uid);

        appointmentRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Appointment appointment = documentSnapshot.toObject(Appointment.class);
                        firestoreCallback.onCallBack(appointment);
                    } else {
                        Log.d("Firestore", "No such document.");
                        firestoreCallback.onCallBack(null);  // No appointment found
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Failed to load appointment.", e);
                    firestoreCallback.onCallBack(null);  // Failed to retrieve appointment
                });
    }

    // Update an existing appointment
    public static void updateAppointment(String uid, Map<String, Object> updates, FirestoreCallback<Boolean> firestoreCallback) {
        DocumentReference appointmentRef = db.collection("appointments").document(uid);

        appointmentRef.update(updates)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "Appointment updated successfully.");
                    firestoreCallback.onCallBack(true);  // Indicate success
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error updating appointment.", e);
                    firestoreCallback.onCallBack(false);  // Indicate failure
                });
    }
}
