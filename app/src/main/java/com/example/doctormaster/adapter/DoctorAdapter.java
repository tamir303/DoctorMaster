package com.example.doctormaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormaster.activities.AppointmentActivity;
import com.example.doctormaster.R;
import com.example.doctormaster.models.Doctor;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {
    private List<Doctor> doctorList;
    private Context context;

    public DoctorAdapter(List<Doctor> doctorList, Context context) {
        this.doctorList = doctorList;
        this.context = context;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);

        // Logging for debugging
        if (doctor != null) {
            String doctorNameView = doctor.getName();
            String doctorExperienceView = "Experience of " + doctor.getExperience() + " Years";
            String doctorLocationView = doctor.getLocation();

            holder.nameTextView.setText(doctorNameView);
            holder.experienceTextView.setText(doctorExperienceView);
            holder.locationTextView.setText(doctorLocationView);
            holder.bookAppointmentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AppointmentActivity.class);
                    intent.putExtra("doctor_uid", doctor.getUid());
                    context.startActivity(intent);
                }
            });
        } else {
            Log.e("DoctorAdapter", "Doctor at position " + position + " is null");
        }
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, experienceTextView, locationTextView;
        Button bookAppointmentButton;

        DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.doctorNameTextView);
            experienceTextView = itemView.findViewById(R.id.doctorExperienceTextView);
            locationTextView = itemView.findViewById(R.id.doctorLocationTextView);
            bookAppointmentButton = itemView.findViewById(R.id.bookAppointmentButton);
        }
    }
}
