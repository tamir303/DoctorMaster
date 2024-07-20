package com.example.doctormaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctormaster.R;
import com.example.doctormaster.activities.fragments.MenuFragment;
import com.example.doctormaster.firebase.FirebaseOperations;
import com.example.doctormaster.logic.appointment.AppointmentCheckCallback;
import com.example.doctormaster.logic.appointment.AppointmentService;
import com.example.doctormaster.logic.appointment.AppointmentServiceImpl;
import com.example.doctormaster.models.Appointment;
import com.example.doctormaster.utils.Date;

public class AppointmentCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_appointment_complete);

        Intent intent = getIntent();
        AppointmentService appointmentService = new AppointmentServiceImpl();

        String userUid = FirebaseOperations.getUserEmail();
        String doctorName = intent.getStringExtra("name");
        String location = intent.getStringExtra("location");
        String time = intent.getStringExtra("time");
        String date = Date.convertLongToDateString(intent.getLongExtra("date", -1));

        appointmentService.processAppointmentRequest(userUid, doctorName, location, time, date, new AppointmentCheckCallback() {
            @Override
            public void onResult(boolean actionResult, Appointment appointment) {
                if (actionResult)
                    setAppointmentCompleteView(appointment);
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_container, new MenuFragment(AppointmentCompleteActivity.this, AppointmentActivity.class))
                .commit();
    }

    private void setAppointmentCompleteView(Appointment appointment) {
        TextView appointmentCompleteDetailsView = findViewById(R.id.appointmentDetails);
        appointmentCompleteDetailsView.setText(appointment.toString());
    }
}
