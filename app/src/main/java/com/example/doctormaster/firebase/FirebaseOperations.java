package com.example.doctormaster.firebase;

import static com.example.doctormaster.utils.Constants.EMPTY_STRING;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseOperations {
    private FirebaseOperations() {
    }

    public static DatabaseReference getDatabaseRef() {
        return FirebaseDatabase.getInstance("https://doctormaster-c7b1f-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
    }

    public static FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    public static String getUserEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d("FirebaseOperations", "Fetched user email: " + user.getEmail());
            return user.getEmail();
        } else {
            Log.e("FirebaseOperations", "No user is signed in!");
            return EMPTY_STRING;
        }
    }
}
