package com.example.doctormaster.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormaster.R;
import com.example.doctormaster.firebase.FirestoreCallback;
import com.example.doctormaster.firebase.database.AppointmentDB;
import com.example.doctormaster.models.Appointment;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.AppointmentViewHolder> {
    private List<Appointment> appointmentsList;

    public AppointmentsAdapter(List<Appointment> appointmentsList) {
        this.appointmentsList = appointmentsList;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appointment = appointmentsList.get(position);
        holder.tvDoctorName.setText("Doctor: " + appointment.getDoctor());
        holder.tvAppointmentDate.setText("Date: " + appointment.getDate());
        holder.tvAppointmentTime.setText("Time: " + appointment.getTime());
        holder.tvAppointmentLocation.setText("Location: " + appointment.getLocation());

        holder.btnAppointmentRemove.setOnClickListener(v -> {
            AppointmentDB.deleteAppointmentByUid(appointment.getUUID(), new FirestoreCallback<Boolean>() {
                @Override
                public void onCallBack(Boolean result) {
                    if (result) {
                        holder.btnAppointmentRemove.setBackgroundColor(Color.GREEN);
                        holder.btnAppointmentRemove.setText("Removed");
                        holder.btnAppointmentRemove.setClickable(false);
                    }
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {

        TextView tvDoctorName, tvAppointmentDate, tvAppointmentTime, tvAppointmentLocation;
        Button btnAppointmentRemove;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDoctorName = itemView.findViewById(R.id.tvDoctorName);
            tvAppointmentDate = itemView.findViewById(R.id.tvAppointmentDate);
            tvAppointmentTime = itemView.findViewById(R.id.tvAppointmentTime);
            tvAppointmentLocation = itemView.findViewById(R.id.tvAppointmentLocation);
            btnAppointmentRemove = itemView.findViewById(R.id.btnRemoveAppointment);
        }
    }
}
