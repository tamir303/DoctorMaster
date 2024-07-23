package com.example.doctormaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doctormaster.R;

import java.util.List;

public class SpecialityAdapter extends RecyclerView.Adapter<SpecialityAdapter.SpecialityViewHolder> {

    private final List<String> specialities;

    public SpecialityAdapter(List<String> specialities) {
        this.specialities = specialities;
    }

    @NonNull
    @Override
    public SpecialityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_speciality, parent, false);
        return new SpecialityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialityViewHolder holder, int position) {
        holder.specialityName.setText(specialities.get(position));
    }

    @Override
    public int getItemCount() {
        return specialities.size();
    }

    public static class SpecialityViewHolder extends RecyclerView.ViewHolder {
        final TextView specialityName;

        public SpecialityViewHolder(@NonNull View itemView) {
            super(itemView);
            specialityName = itemView.findViewById(R.id.tvSpecialityName);
        }
    }
}
