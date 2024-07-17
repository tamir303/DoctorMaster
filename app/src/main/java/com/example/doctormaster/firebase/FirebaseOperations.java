package com.example.doctormaster.firebase;

import com.google.firebase.auth.FirebaseAuth;
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
}
