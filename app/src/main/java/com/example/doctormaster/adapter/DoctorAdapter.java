package com.example.doctormaster.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormaster.activities.AppointmentActivity;
import com.example.doctormaster.R;
import com.example.doctormaster.models.Doctor;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {
    private final List<Doctor> doctorList;
    private final Context context;
    private final String field;
    private final String speciality;

    public DoctorAdapter(List<Doctor> doctorList, Context context, String field, String speciality) {
        this.doctorList = doctorList;
        this.context = context;
        this.field = field;
        this.speciality = speciality;
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

            int iconResId = context.getResources().getIdentifier(doctor.getImage(), "drawable", context.getPackageName());
            holder.doctorImageView.setImageResource(iconResId);

            holder.bookAppointmentButton.setOnClickListener(v -> {
                Intent intent = new Intent(context, AppointmentActivity.class);
                intent.putExtra("doctor_uid", doctor.getUid());
                intent.putExtra("speciality", speciality);
                intent.putExtra("field", field);

                context.startActivity(intent);
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
        final TextView nameTextView;
        final TextView experienceTextView;
        final TextView locationTextView;
        final Button bookAppointmentButton;
        final ImageView doctorImageView;

        DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.doctorNameTextView);
            experienceTextView = itemView.findViewById(R.id.doctorExperienceTextView);
            locationTextView = itemView.findViewById(R.id.doctorLocationTextView);
            bookAppointmentButton = itemView.findViewById(R.id.bookAppointmentButton);
            doctorImageView = itemView.findViewById(R.id.doctorImageView);
        }
    }
}
