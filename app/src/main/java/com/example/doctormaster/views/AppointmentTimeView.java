package com.example.doctormaster.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.doctormaster.utils.AppointmentTimesUtils;

import java.util.List;

public class AppointmentTimeView extends LinearLayout {
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
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
    }

    public void setAppointmentTimes(int startHour, int finishHour, int breakHour, int breakLength) {
        removeAllViews();
        List<String> availableTimes = AppointmentTimesUtils.calculateAvailableTimes(startHour, finishHour, breakHour, breakLength);

        for (String time : availableTimes) {
            Button timeButton = new Button(getContext());
            timeButton.setText(time);
            timeButton.setGravity(Gravity.CENTER);
            timeButton.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            ));

            addView(timeButton);
        }
    }
}
