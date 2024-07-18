package com.example.doctormaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormaster.R;
import com.example.doctormaster.adapter.DoctorAdapter;
import com.example.doctormaster.firebase.FirestoreCallback;
import com.example.doctormaster.firebase.database.DoctorDB;
import com.example.doctormaster.models.Doctor;
import com.example.doctormaster.utils.Utils;

import java.util.List;

public class DoctorDetailActivity extends AppCompatActivity {
    private RecyclerView doctorRecyclerView;
    private DoctorAdapter doctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_doctor_detail);

        Intent intent = getIntent();
        String speciality = intent.getStringExtra("speciality");
        String field = intent.getStringExtra("field");

        TextView specialityTextView = findViewById(R.id.specialityTextView);
        specialityTextView.setText(speciality);

        doctorRecyclerView = findViewById(R.id.doctorRecyclerView);
        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DoctorDB.getDoctorsByFieldAndSpeciality(
                this,
                new FirestoreCallback<List<Doctor>>() {
                    @Override
                    public void onCallBack(List<Doctor> doctorList) {
                        if (doctorList.isEmpty()) {
                            Utils.showToast(DoctorDetailActivity.this, "No doctors found!");
                        } else {
                            doctorAdapter = new DoctorAdapter(doctorList, DoctorDetailActivity.this);
                            doctorRecyclerView.setAdapter(doctorAdapter);
                        }
                    }
                },
                field,
                speciality
        );
    }
}
