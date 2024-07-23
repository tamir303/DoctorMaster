package com.example.doctormaster.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormaster.R;
import com.example.doctormaster.adapter.AppointmentsAdapter;
import com.example.doctormaster.firebase.FirestoreCallback;
import com.example.doctormaster.firebase.database.AppointmentDB;
import com.example.doctormaster.models.Appointment;

import java.util.List;

public class UserAppointmentsFragment extends Fragment {
    private RecyclerView rvAppointments;
    private AppointmentsAdapter appointmentsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_appointments, container, false);

        rvAppointments = view.findViewById(R.id.rvAppointments);

        // Set up RecyclerView
        AppointmentDB.loadAppointmentsByUser(appointmentsList -> {
            appointmentsAdapter = new AppointmentsAdapter(appointmentsList);
            rvAppointments.setLayoutManager(new LinearLayoutManager(getContext()));
            rvAppointments.setAdapter(appointmentsAdapter);
        });

        return view;
    }
}
