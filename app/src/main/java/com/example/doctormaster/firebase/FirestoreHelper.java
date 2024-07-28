package com.example.doctormaster.firebase;

import android.content.Context;
import android.util.Log;

import com.example.doctormaster.R;
import com.example.doctormaster.models.Doctor;
import com.example.doctormaster.utils.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class FirestoreHelper {
    private static final DatabaseReference db = FirebaseOperations.getDatabaseRef();

        public static void uploadInitialDoctors(Context context) {
        try {
            // Read JSON file from assets
            InputStreamReader reader = new InputStreamReader(context.getResources().openRawResource(R.raw.doctors_init));

            // Parse JSON file to a list of Doctor objects
            Type doctorListType = new TypeToken<List<Doctor>>() {}.getType();
            List<Doctor> doctors = new Gson().fromJson(reader, doctorListType);

            // Upload each doctor object to Realtime Database
            for (Doctor doctor : doctors) {
                db.child(Constants.DOCTOR_DB).child(doctor.getUid()).setValue(doctor)
                        .addOnSuccessListener(aVoid -> Log.d("FirebaseDatabase", "Doctor successfully added with ID: " + doctor.getUid()))
                        .addOnFailureListener(e -> Log.e("FirebaseDatabase", "Error adding doctor", e));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("FirebaseDatabase", "Error reading or parsing JSON", e);
        }
    }
}
