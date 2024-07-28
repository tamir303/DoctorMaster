package com.example.doctormaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctormaster.R;
import com.example.doctormaster.activities.fragments.MenuFragment;
import com.example.doctormaster.firebase.database.DoctorDB;
import com.example.doctormaster.models.Doctor;
import com.example.doctormaster.utils.Date;
import com.example.doctormaster.utils.Utils;
import com.example.doctormaster.views.AppointmentTimeView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Optional;


public class AppointmentActivity extends BaseActivity {

    private CalendarView calenderView;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        getLayoutInflater().inflate(R.layout.activity_appointment, findViewById(R.id.container));

        this.calenderView = findViewById(R.id.calendar_view);
        Date.setCalenderDefaultConfiguration(calenderView);

        Intent intent = getIntent();
        String doctor_uid = intent.getStringExtra("doctor_uid");
        String field = intent.getStringExtra("field");
        String speciality = intent.getStringExtra("speciality");

        Log.d("AppointmentActivity", "Received doctor UID: " + doctor_uid);

        DoctorDB.findDoctorByUid(AppointmentActivity.this, doctor_uid,
                doctor -> {
                    if (doctor != null) {
                        Log.i("FirebaseDatabase", "Doctor" + doctor_uid + " was found!");
                        this.calenderView = findViewById(R.id.calendar_view);
                        Date.setCalenderDefaultConfiguration(calenderView);

                        this.calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                            @Override
                            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                                Calendar selectedCalendar = Calendar.getInstance();
                                selectedCalendar.set(year, month, dayOfMonth);

                                setDoctorAvailabilityView(doctor, selectedCalendar.getTimeInMillis());
                                Utils.showToast(AppointmentActivity.this, "Selected Date: " + selectedCalendar.getTime().getMonth() + "/" + selectedCalendar.getTime().getDay());
                            }
                        });

                        setCurrentDoctorView(doctor);
                        setDoctorAvailabilityView(doctor, calenderView.getDate());
                        Log.d("AppointmentActivity", "Present doctor: "+ doctor_uid + " appointment details!");
                    } else {
                        Utils.showToast(AppointmentActivity.this, "No doctor found!");
                        Log.e("FirebaseDatabase", "ERROR, DoctorUid" + doctor_uid + " return no results!");
                    }
                }
        );

        HashMap<String, Object> extraArgs = new HashMap<>();
        extraArgs.put("speciality", speciality);
        extraArgs.put("field", field);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_container, MenuFragment.newInstance(AppointmentActivity.class, DoctorDetailActivity.class, Optional.of(extraArgs)))
                .commit();
    }

    @Override
    public void InitializeViews() {

    }

    @Override
    public void setButtonListeners() {

    }

    private void setCurrentDoctorView(Doctor doctor) {
        Log.d("AppointmentActivity", "Setting current doctor view for: " + doctor.getName());

        TextView doctorNameText = findViewById(R.id.doctor_name);
        doctorNameText.setText(doctor.getName());

        TextView doctorDescText = findViewById(R.id.doctor_description);
        doctorDescText.setText(doctor.getDescription());

        RatingBar doctorRateBar = findViewById(R.id.doctor_rating);
        doctorRateBar.setRating((float) doctor.getRating());

        ImageView doctorImageView = findViewById(R.id.doctor_image);
        int iconResId = this.getResources().getIdentifier(doctor.getImage(), "drawable", this.getPackageName());
        doctorImageView.setImageResource(iconResId);
    }

    private void setDoctorAvailabilityView(Doctor doctor, Long date) {
        Log.d("AppointmentActivity", "Setting availability view for doctor: " + doctor.getName());
        Log.d("AppointmentActivity", "Set date to check availability: "+ date);
        AppointmentTimeView appointmentTimeView = findViewById(R.id.appointment_times_container);
        appointmentTimeView.setAppointmentTimes(
                doctor.getStartHour(),
                doctor.getFinishHour(),
                doctor.getBreakHour(),
                doctor.getBreakLength(),
                date,
                doctor
        );
    }
}
