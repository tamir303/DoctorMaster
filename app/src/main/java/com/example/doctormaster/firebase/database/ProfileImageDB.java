package com.example.doctormaster.firebase.database;

import static com.example.doctormaster.firebase.FirebaseOperations.getAuth;
import static com.example.doctormaster.firebase.FirebaseOperations.getStorageRef;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.doctormaster.firebase.FirebaseOperations;
import com.example.doctormaster.firebase.FirestoreCallback;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public abstract class ProfileImageDB {
    private static final DatabaseReference db = FirebaseOperations.getDatabaseRef();

    public static void uploadImageToFirebase(Uri imageUri) {
        FirebaseUser user = getAuth().getCurrentUser();
        if (user != null) {
            StorageReference storageReference = getStorageRef();
            assert storageReference != null;
            storageReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                // Save the download URL to the user's profile
                                saveImageUrlToFirestore(uri.toString());
                            }))
                    .addOnFailureListener(e -> Log.e("Images", "Failed to upload image!"));
        }
    }

    private static void saveImageUrlToFirestore(String downloadUrl) {
        FirebaseUser user = getAuth().getCurrentUser();
        if (user != null) {
            db.child("users").child(user.getUid())
                    .setValue("profileImageUrl", downloadUrl)
                    .addOnSuccessListener(aVoid ->  Log.i("Images",  "Image URL Saved"))
                    .addOnFailureListener(e ->  Log.e("Images", "Failed to Save Image URL"));
        }
    }

    public static void loadProfileImage(FirestoreCallback<String> firestoreCallback) {
        FirebaseUser user = getAuth().getCurrentUser();
        if (user != null) {
            db.child("users").child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String imageUrl = snapshot.getValue(String.class);
                            assert imageUrl != null;
                            if (!imageUrl.isEmpty())
                                firestoreCallback.onCallBack(imageUrl);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Images", "Failed to Load Image URL!");
                    }
            });
        }
    }
}
