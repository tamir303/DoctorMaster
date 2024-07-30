package com.example.doctormaster.activities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctormaster.R;
import com.example.doctormaster.activities.fragments.MenuFragment;
import com.example.doctormaster.firebase.FirebaseOperations;
import com.example.doctormaster.logic.appointment.AppointmentService;
import com.example.doctormaster.logic.appointment.AppointmentServiceImpl;
import com.example.doctormaster.models.Appointment;
import com.example.doctormaster.utils.Date;

import java.util.Calendar;
import java.util.TimeZone;

public class AppointmentCompleteActivity extends BaseActivity {
    private Button addToCalendarButton;
    private Appointment currentAppointment;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        getLayoutInflater().inflate(R.layout.activity_appointment_complete, findViewById(R.id.container));

        Intent intent = getIntent();
        AppointmentService appointmentService = new AppointmentServiceImpl();

        String userUid = FirebaseOperations.getUserEmail();
        String doctorName = intent.getStringExtra("name");
        String location = intent.getStringExtra("location");
        String time = intent.getStringExtra("time");
        String date = Date.convertLongToDateString(intent.getLongExtra("date", -1));

        appointmentService.processAppointmentRequest(userUid, doctorName, location, time, date, (actionResult, appointment) -> {
            if (actionResult) {
                setAppointmentCompleteView(appointment);
                this.currentAppointment = appointment;
                InitializeViews();
                setButtonListeners();
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_container, MenuFragment.newInstance(AppointmentCompleteActivity.class, AppointmentActivity.class, java.util.Optional.empty()))
                .commit();
    }

    @Override
    public void InitializeViews() {
        this.addToCalendarButton = findViewById(R.id.addToCalendarButton);
    }

    @Override
    public void setButtonListeners() {
        this.addToCalendarButton.setOnClickListener(v -> {
            addEventToCalendar(this.currentAppointment);
        });
    }

    private void setAppointmentCompleteView(Appointment appointment) {
        TextView appointmentCompleteDetailsView = findViewById(R.id.appointmentDetails);
        appointmentCompleteDetailsView.setText(appointment.toString());
    }

    private void addEventToCalendar(Appointment appointment) {
        Calendar beginTime = Calendar.getInstance();
        beginTime.setTimeInMillis(Date.convertStringToDateLong(appointment.getDate() + " " + appointment.getTime()));
        Calendar endTime = Calendar.getInstance();
        endTime.setTimeInMillis(beginTime.getTimeInMillis() + 1800000L); // Add 30 minutes

        // Create an Intent to add an event to the calendar
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");

        // Set the event details
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        intent.putExtra(CalendarContract.Events.TITLE, "Doctor appointment!");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Appointment for: " + appointment.getDoctor() + "\n" + appointment.getDate() + " | " + appointment.getTime() + " at " + appointment.getLocation());
        intent.putExtra(CalendarContract.Events.ALL_DAY, false); // Set to true if it's an all-day event
        intent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

        // Start the intent
        startActivity(intent);
    }
}
