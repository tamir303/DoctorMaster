package com.example.doctormaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RawRes;

import com.example.doctormaster.R;
import com.example.doctormaster.activities.fragments.MenuFragment;
import com.example.doctormaster.models.MedicalField;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MedicalFieldDetailsActivity extends BaseActivity {
    GridLayout fieldsGrid;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        getLayoutInflater().inflate(R.layout.activity_medicalfield_details, findViewById(R.id.container));

        InitializeViews();
        setDetailsView();

        HashMap<String, Object> extraArgs = new HashMap<>();
        extraArgs.put("USER_TYPE", "patient");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_container, MenuFragment.newInstance(MedicalFieldDetailsActivity.class, LoginActivity.class, Optional.of(extraArgs)))
                .commit();
    }

    @Override
    public void InitializeViews() {
        fieldsGrid = findViewById(R.id.gridLayout);
    }

    @Override
    public void setButtonListeners() {

    }

    private void setDetailsView() {
        List<MedicalField> medicalFields = loadMedicalFieldsFromJson(R.raw.medical_fields);

        LayoutInflater inflater = LayoutInflater.from(this);
        for (MedicalField field : medicalFields) {
            View itemView = inflater.inflate(R.layout.item_medicalfield, fieldsGrid, false);

            ImageView icon = itemView.findViewById(R.id.icon);
            TextView name = itemView.findViewById(R.id.name);

            // Set the icon resource dynamically
            int iconResId = getResources().getIdentifier(field.getIcon(), "drawable", getPackageName());

            icon.setImageResource(iconResId);
            name.setText(field.getName());

            itemView.setOnClickListener(v -> {
                // Start MedicalFieldActivity and pass data
                Intent intent = new Intent(MedicalFieldDetailsActivity.this, MedicalFieldActivity.class);
                intent.putExtra("name", field.getName());
                intent.putExtra("iconResId", iconResId);
                startActivity(intent);
            });

            fieldsGrid.addView(itemView);
        }
    }

    private List<MedicalField> loadMedicalFieldsFromJson(@RawRes int resourceId) {
        InputStreamReader reader = new InputStreamReader(getResources().openRawResource(resourceId));
        Type listType = new TypeToken<List<MedicalField>>() {}.getType();
        return new Gson().fromJson(reader, listType);
    }
}
