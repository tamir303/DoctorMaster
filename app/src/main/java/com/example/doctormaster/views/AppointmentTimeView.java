package com.example.doctormaster.views;

import static com.example.doctormaster.utils.Constants.APPOINTMENT_TIME_VIEW_COLUMNS;
import static com.example.doctormaster.utils.Constants.BUTTON_SIZE_DP;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.annotation.Nullable;

import com.example.doctormaster.activities.AppointmentCompleteActivity;
import com.example.doctormaster.models.Doctor;
import com.example.doctormaster.utils.AppointmentTimesUtils;

import java.util.List;

public class AppointmentTimeView extends GridLayout {
    public AppointmentTimeView(Context context) {
        super(context);
        init();
    }

    public AppointmentTimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppointmentTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setColumnCount(5); // Set the number of columns to 5
        setRowCount(5); // Set the number of rows to 5
        setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        setUseDefaultMargins(true);
    }

    public void setAppointmentTimes(int startHour, int finishHour, int breakHour, int breakLength, long date, Doctor doctor) {
        removeAllViews();
        List<String> availableTimes = AppointmentTimesUtils.calculateAvailableTimes(startHour, finishHour, breakHour, breakLength);

        int index = 0;
        for (String time : availableTimes) {
            Button timeButton = new Button(getContext());
            timeButton.setText(time);
            timeButton.setGravity(Gravity.CENTER);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.rowSpec = GridLayout.spec(index / APPOINTMENT_TIME_VIEW_COLUMNS, 1);
            params.columnSpec = GridLayout.spec(index % APPOINTMENT_TIME_VIEW_COLUMNS, 1);
            params.setGravity(Gravity.FILL);

            int buttonSizePx = (int) (BUTTON_SIZE_DP * getResources().getDisplayMetrics().density);
            params.width = buttonSizePx;
            params.height = buttonSizePx;
            params.setMargins(8, 8, 8, 8);

            timeButton.setLayoutParams(params);

            timeButton.setOnClickListener(view -> {
                Intent intent = new Intent(getContext(), AppointmentCompleteActivity.class);
                intent.putExtra("name", doctor.getName());
                intent.putExtra("location", doctor.getLocation());
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                getContext().startActivity(intent);
            });

            addView(timeButton);
            index++;
        }
    }
}
