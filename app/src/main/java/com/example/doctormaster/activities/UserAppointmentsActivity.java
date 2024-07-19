package com.example.doctormaster.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctormaster.R;
import com.example.doctormaster.activities.fragments.MenuFragment;
import com.example.doctormaster.activities.fragments.UserAppointmentsFragment;

public class UserAppointmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointments);

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
    }
}
