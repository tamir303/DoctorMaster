package com.example.doctormaster.activities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.doctormaster.R;
import com.example.doctormaster.activities.LoginActivity;
import com.example.doctormaster.activities.UserAppointmentsActivity;

import java.util.HashMap;
import java.util.Optional;

public class MenuFragment extends Fragment {
    private Class<? extends AppCompatActivity> currentActivity;
    private Class<? extends AppCompatActivity> previousActivity;
    private static final String ARG_CURRENT_ACTIVITY = "current_activity";
    private static final String ARG_PREVIOUS_ACTIVITY = "previous_activity";
    private static final String ARG_EXTRA_ARGS = "extra_args";
    private HashMap<String, Object> extraArgs;

    public MenuFragment() {
    }

    public static MenuFragment newInstance(Class<? extends AppCompatActivity> currentActivity,
                                           Class<? extends AppCompatActivity> previousActivity,
                                           Optional<HashMap<String, Object>> extraArgs) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CURRENT_ACTIVITY, currentActivity);
        args.putSerializable(ARG_PREVIOUS_ACTIVITY, previousActivity);
        if (extraArgs.isPresent()) {
            args.putSerializable(ARG_EXTRA_ARGS, extraArgs.get());
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentActivity = (Class<? extends AppCompatActivity>) getArguments().getSerializable(ARG_CURRENT_ACTIVITY);
            previousActivity = (Class<? extends AppCompatActivity>) getArguments().getSerializable(ARG_PREVIOUS_ACTIVITY);
            extraArgs = (HashMap<String, Object>) getArguments().getSerializable(ARG_EXTRA_ARGS);
        }
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

        ImageButton btnGoBack = view.findViewById(R.id.btnGoBack);
        ImageButton btnGoToProfile = view.findViewById(R.id.btnGoToProfile);
        ImageButton btnSwitchUsers = view.findViewById(R.id.btnSwitchUsers);

        btnGoBack.setOnClickListener(v -> {
            // Navigate back
            Log.d("Menu", "Switch To Previous Screen!");
            if (previousActivity != null) {
                Intent intent = new Intent(getActivity(), previousActivity);
                if (extraArgs != null) {
                    for (String key : extraArgs.keySet()) {
                        Object value = extraArgs.get(key);
                        if (value instanceof String) {
                            intent.putExtra(key, (String) value);
                        } else if (value instanceof Integer) {
                            intent.putExtra(key, (Integer) value);
                        } // Add more types as needed
                    }
                }
                startActivity(intent);
            }
        });

        btnGoToProfile.setOnClickListener(v -> {
            Log.d("Menu", "Switch To User Profile!");
            Intent intent = new Intent(getActivity(), UserAppointmentsActivity.class);
            startActivity(intent);
        });

        btnSwitchUsers.setOnClickListener(v -> {
            Log.d("Menu", "Back to login screen!");
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }
}
