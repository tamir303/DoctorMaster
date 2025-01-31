package com.example.doctormaster.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.request.RequestOptions;
import com.example.doctormaster.R;
import com.example.doctormaster.activities.fragments.MenuFragment;
import com.example.doctormaster.activities.fragments.UserAppointmentsFragment;
import com.example.doctormaster.firebase.FirebaseOperations;
import com.example.doctormaster.firebase.FirestoreCallback;
import com.example.doctormaster.firebase.database.ProfileImageDB;

import java.util.HashMap;
import java.util.Optional;

public class UserAppointmentsActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imgPlaceholder;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointments);

        TextView tvGreeting = findViewById(R.id.tvGreeting);
        imgPlaceholder = findViewById(R.id.imgPlaceholder);

        HashMap<String, Object> extraArgs = new HashMap<>();
        extraArgs.put("USER_TYPE", "patient");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_container, MenuFragment.newInstance(UserAppointmentsActivity.class, MedicalFieldDetailsActivity.class, Optional.of(extraArgs)))
                .commit();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new UserAppointmentsFragment())
                    .commit();
        }

        // Set user email in greeting
        String userEmail = FirebaseOperations.getUserEmail();
        tvGreeting.setText("Hello, " + userEmail.split("@")[0]);

        imgPlaceholder.setOnClickListener(v -> openImageChooser());
        ProfileImageDB.loadProfileImage(new FirestoreCallback<String>() {
            @Override
            public void onCallBack(String result) {
                if (result != null) {
                    Glide.with(getApplicationContext())
                                    .load(result + "&w=300&h=300")
                                    .apply(new RequestOptions()
                                        .placeholder(R.drawable.user_login_placeholder)
                                        .centerCrop())
                                    .into(imgPlaceholder);
                }
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            this.imgPlaceholder.setImageURI(imageUri);
            ProfileImageDB.uploadImageToFirebase(imageUri);
        }
    }
}
