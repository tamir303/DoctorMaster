package com.example.doctormaster.firebase.database;

import static com.example.doctormaster.firebase.FirebaseOperations.getAuth;
import static com.example.doctormaster.firebase.FirebaseOperations.getStorageRef;
import static com.example.doctormaster.firebase.FirebaseOperations.getDatabaseRef;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.doctormaster.firebase.FirestoreCallback;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public abstract class ProfileImageDB {
    private static final StorageReference storageRef = getStorageRef();
    private static final DatabaseReference databaseRef = getDatabaseRef();

    public static void uploadImageToFirebase(Uri imageUri) {
        FirebaseUser user = getAuth().getCurrentUser();
        if (user != null) {
            StorageReference userImageRef = storageRef.child("profile_images/" + user.getUid() + ".jpg");
            userImageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> userImageRef.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                // Save the download URL to the user's profile
                                saveImageUrlToDatabase(uri.toString());
                            }))
                    .addOnFailureListener(e -> Log.e("Images", "Failed to upload image!", e));
        }
    }

    private static void saveImageUrlToDatabase(String downloadUrl) {
        FirebaseUser user = getAuth().getCurrentUser();
        if (user != null) {
            databaseRef.child("users").child(user.getUid()).child("profileImageUrl")
                    .setValue(downloadUrl)
                    .addOnSuccessListener(aVoid -> Log.i("Images", "Image URL Saved"))
                    .addOnFailureListener(e -> Log.e("Images", "Failed to Save Image URL", e));
        }
    }

    public static void loadProfileImage(FirestoreCallback<String> firestoreCallback) {
        FirebaseUser user = getAuth().getCurrentUser();
        if (user != null) {
            databaseRef.child("users").child(user.getUid()).child("profileImageUrl")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String imageUrl = snapshot.getValue(String.class);
                                if (imageUrl != null && !imageUrl.isEmpty()) {
                                    firestoreCallback.onCallBack(imageUrl);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Images", "Failed to Load Image URL!", error.toException());
                        }
                    });
        }
    }
}
