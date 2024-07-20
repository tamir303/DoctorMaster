package com.example.doctormaster.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctormaster.R;
import com.example.doctormaster.activities.fragments.MenuFragment;
import com.example.doctormaster.activities.fragments.UserAppointmentsFragment;
import com.example.doctormaster.firebase.FirebaseOperations;

public class UserAppointmentsActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imgPlaceholder;
    private TextView tvGreeting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointments);

        tvGreeting = findViewById(R.id.tvGreeting);
        imgPlaceholder = findViewById(R.id.imgPlaceholder);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new UserAppointmentsFragment())
                    .commit();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.menu_container, new MenuFragment(UserAppointmentsActivity.this, MedicalFieldDetailsActivity.class))
                    .commit();
        }

        // Set user email in greeting
        String userEmail = FirebaseOperations.getUserEmail();
        this.tvGreeting.setText("Hello, " + userEmail);

        imgPlaceholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        // TODO IMPLEMENT IMAGE UPLOAD
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
        }
    }
}
