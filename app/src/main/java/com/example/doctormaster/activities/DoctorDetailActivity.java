package com.example.doctormaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormaster.R;
import com.example.doctormaster.activities.fragments.MenuFragment;
import com.example.doctormaster.adapter.DoctorAdapter;
import com.example.doctormaster.firebase.database.DoctorDB;
import com.example.doctormaster.utils.Utils;

import java.util.HashMap;
import java.util.Optional;


public class DoctorDetailActivity extends BaseActivity {
    private RecyclerView doctorRecyclerView;
    private DoctorAdapter doctorAdapter;
    private TextView specialityTextView;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        getLayoutInflater().inflate(R.layout.activity_doctor_detail, findViewById(R.id.container));

        Intent intent = getIntent();
        String speciality = intent.getStringExtra("speciality");
        String field = intent.getStringExtra("field");

        InitializeViews();

        specialityTextView.setText(speciality);
        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DoctorDB.getDoctorsByFieldAndSpeciality(
                this,
                doctorList -> {
                    if (doctorList.isEmpty()) {
                        Utils.showToast(DoctorDetailActivity.this, "No doctors found!");
                    } else {
                        doctorAdapter = new DoctorAdapter(doctorList, DoctorDetailActivity.this, field, speciality);
                        doctorRecyclerView.setAdapter(doctorAdapter);
                    }
                },
                field,
                speciality
        );

        HashMap<String, Object> extraArgs = new HashMap<>();
        extraArgs.put("name", field);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_container, MenuFragment.newInstance(DoctorDetailActivity.class, MedicalFieldActivity.class, Optional.of(extraArgs)))
                .commit();
    }

    @Override
    public void InitializeViews() {
        specialityTextView = findViewById(R.id.specialityTextView);
        doctorRecyclerView = findViewById(R.id.doctorRecyclerView);
    }

    @Override
    public void setButtonListeners() {

    }
}
