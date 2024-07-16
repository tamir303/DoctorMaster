package com.example.doctormaster.firebase;

import android.content.Context;
import android.util.Log;

import com.example.doctormaster.models.Doctor;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class FirestoreHelper {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void uploadInitialDoctors(Context context) {
        try {
            // Read JSON file from assets
            InputStreamReader reader = new InputStreamReader(context.getAssets().open("doctors_init.json"));

            // Parse JSON file to a list of Doctor objects
            Type doctorListType = new TypeToken<List<Doctor>>() {}.getType();
            List<Doctor> doctors = new Gson().fromJson(reader, doctorListType);

            // Reference to the "doctors" collection in Firestore
            CollectionReference doctorsRef = db.collection("doctors");

            // Upload each doctor object to Firestore
            for (Doctor doctor : doctors) {
                doctorsRef.add(doctor)
                        .addOnSuccessListener(documentReference -> {
                            Log.d("Firestore", "Doctor added with ID: " + documentReference.getId());
                        })
                        .addOnFailureListener(e -> {
                            Log.e("Firestore", "Error adding doctor", e);
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Firestore", "Error reading or parsing JSON", e);
        }
    }
}
