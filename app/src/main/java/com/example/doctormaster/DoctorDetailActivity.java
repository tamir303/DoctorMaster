package com.example.doctormaster;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormaster.adapter.DoctorAdapter;
import com.example.doctormaster.firebase.database.AppointmentDB;
import com.example.doctormaster.firebase.FirestoreCallback;
import com.example.doctormaster.firebase.database.DoctorDB;
import com.example.doctormaster.models.Doctor;
import com.example.doctormaster.utils.Utils;

import java.util.List;

public class DoctorDetailActivity extends AppCompatActivity {
    private RecyclerView doctorRecyclerView;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> doctors;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_doctor_detail);

        doctorRecyclerView = findViewById(R.id.doctorRecyclerView);
        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DoctorDB.loadDoctors(this, new FirestoreCallback<List<Doctor>>() {
            @Override
            public void onCallBack(List<Doctor> doctorList) {
                if (doctorList.isEmpty()) {
                    Utils.showToast(DoctorDetailActivity.this, "No doctors found!");
                } else {
                    doctorAdapter = new DoctorAdapter(doctorList, DoctorDetailActivity.this);
                    doctorRecyclerView.setAdapter(doctorAdapter);
                    doctors = doctorList;
                }
            }
        });

    }
}
