package com.example.doctormaster.activities.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.doctormaster.R;
import com.example.doctormaster.activities.LoginActivity;
import com.example.doctormaster.activities.UserAppointmentsActivity;
import com.example.doctormaster.utils.Utils;

public class MenuFragment extends Fragment  {
    private final AppCompatActivity currentActivity;
    private final Class<? extends AppCompatActivity> previousActivity;
    public MenuFragment(AppCompatActivity currentActivity, Class<? extends AppCompatActivity> previousActivity) {
        this.currentActivity = currentActivity;
        this.previousActivity = previousActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnGoBack = view.findViewById(R.id.btnGoBack);
        Button btnGoToProfile = view.findViewById(R.id.btnGoToProfile);
        Button btnSwitchUsers = view.findViewById(R.id.btnSwitchUsers);

        btnGoBack.setOnClickListener(v -> {
            // Navigate back
            Log.d("Menu", "Switch To Previous Screen!");
            Utils.navigateToNextActivity(currentActivity, previousActivity);
        });

        btnGoToProfile.setOnClickListener(v -> {
            Log.d("Menu", "Switch To User Profile!");
            Utils.navigateToNextActivity(currentActivity, UserAppointmentsActivity.class);
        });

        btnSwitchUsers.setOnClickListener(v -> {
            Log.d("Menu", "Back to login screen!");
            Utils.navigateToNextActivity(currentActivity, LoginActivity.class);
        });
    }
}
