package com.example.doctormaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctormaster.R;
import com.example.doctormaster.activities.fragments.MenuFragment;
import com.example.doctormaster.firebase.FirestoreCallback;
import com.example.doctormaster.firebase.database.DoctorDB;
import com.example.doctormaster.models.Doctor;
import com.example.doctormaster.utils.Date;
import com.example.doctormaster.utils.Utils;
import com.example.doctormaster.views.AppointmentTimeView;

public class AppointmentActivity extends AppCompatActivity {

    private CalendarView calenderView;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_appointment);

        this.calenderView = findViewById(R.id.calendar_view);
        Date.setCalenderDefaultConfiguration(calenderView);

        Intent intent = getIntent();
        String doctor_uid = intent.getStringExtra("doctor_uid");
        Log.d("AppointmentActivity", "Received doctor UID: " + doctor_uid);

        DoctorDB.findDoctorByUid(AppointmentActivity.this, doctor_uid,
                new FirestoreCallback<Doctor>() {
                    @Override
                    public void onCallBack(Doctor doctor) {
                        if (doctor != null) {
                            Log.i("FirebaseDatabase", "Doctor" + doctor_uid + " was found!");
                            setCurrentDoctorView(doctor);
                            setDoctorAvailabilityView(doctor);
                        } else {
                            Utils.showToast(AppointmentActivity.this, "No doctor found!");
                            Log.e("FirebaseDatabase", "ERROR, DoctorUid" + doctor_uid + " return no results!");
                        }
                    }
                }
        );

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_container, new MenuFragment(AppointmentActivity.this, DoctorDetailActivity.class))
                .commit();
    }

    private void setCurrentDoctorView(Doctor doctor) {
        Log.d("AppointmentActivity", "Setting current doctor view for: " + doctor.getName());

        TextView doctorNameText = findViewById(R.id.doctor_name);
        doctorNameText.setText(doctor.getName());

        TextView doctorDescText = findViewById(R.id.doctor_description);
        doctorDescText.setText(doctor.getDescription());

        RatingBar doctorRateBar = findViewById(R.id.doctor_rating);
        doctorRateBar.setRating((float) doctor.getRating());
    }

    private void setDoctorAvailabilityView(Doctor doctor) {
        Log.d("AppointmentActivity", "Setting availability view for doctor: " + doctor.getName());

        AppointmentTimeView appointmentTimeView = findViewById(R.id.appointment_times_container);
        appointmentTimeView.setAppointmentTimes(
                doctor.getStartHour(),
                doctor.getFinishHour(),
                doctor.getBreakHour(),
                doctor.getBreakLength(),
                calenderView.getDate(),
                doctor
        );
    }
}
