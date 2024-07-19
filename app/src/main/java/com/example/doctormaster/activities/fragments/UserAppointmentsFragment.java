package com.example.doctormaster.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormaster.R;
import com.example.doctormaster.adapter.AppointmentsAdapter;
import com.example.doctormaster.firebase.FirebaseOperations;
import com.example.doctormaster.firebase.FirestoreCallback;
import com.example.doctormaster.firebase.database.AppointmentDB;
import com.example.doctormaster.logic.AppointmentService;
import com.example.doctormaster.logic.AppointmentServiceImpl;
import com.example.doctormaster.models.Appointment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserAppointmentsFragment extends Fragment {
    private TextView tvGreeting;
    private ImageView imgPlaceholder;
    private RecyclerView rvAppointments;
    private AppointmentsAdapter appointmentsAdapter;
    private List<Appointment> appointmentsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_appointments, container, false);

        tvGreeting = view.findViewById(R.id.tvGreeting);
        imgPlaceholder = view.findViewById(R.id.imgPlaceholder);
        rvAppointments = view.findViewById(R.id.rvAppointments);

        // Set up RecyclerView
        AppointmentDB.loadAppointmentsByUser(new FirestoreCallback<List<Appointment>>() {
            @Override
            public void onCallBack(List<Appointment> appointmentsList) {
                appointmentsAdapter = new AppointmentsAdapter(appointmentsList);
                rvAppointments.setLayoutManager(new LinearLayoutManager(getContext()));
                rvAppointments.setAdapter(appointmentsAdapter);
            }
        });

        // Set user email in greeting
        String userEmail = FirebaseOperations.getUserEmail();
        tvGreeting.setText("Hello, " + userEmail);

        return view;
    }
}
