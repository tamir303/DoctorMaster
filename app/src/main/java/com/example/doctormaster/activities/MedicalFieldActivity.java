package com.example.doctormaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RawRes;

import com.example.doctormaster.R;
import com.example.doctormaster.activities.fragments.MenuFragment;
import com.example.doctormaster.models.MedicalFieldDetails;
import com.example.doctormaster.views.FieldItemView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class MedicalFieldActivity extends BaseActivity {
    private ImageView headerIcon;
    private TextView headerTitle;
    private GridLayout fieldsGrid;
    private String fieldName;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        getLayoutInflater().inflate(R.layout.activity_medicalfield, findViewById(R.id.container));

        Intent intent = getIntent();
        fieldName = intent.getStringExtra("name");
        int iconResId = intent.getIntExtra("iconResId", -1);

        InitializeViews();

        if (iconResId != -1)
            headerIcon.setImageResource(iconResId);
        headerTitle.setText(fieldName);

        setDetailsView();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_container, new MenuFragment(MedicalFieldActivity.this, MedicalFieldDetailsActivity.class))
                .commit();
    }

    @Override
    public void InitializeViews() {
        headerIcon = findViewById(R.id.headerIcon);
        headerTitle = findViewById(R.id.headerTitle);
        fieldsGrid = findViewById(R.id.gridLayout);
    }

    @Override
    public void setButtonListeners() {

    }

    private void setDetailsView() {
        MedicalFieldDetails fieldDetails = loadMedicalFieldDetailsFromJson(R.raw.medical_fields_details, fieldName);

        assert fieldDetails != null;
        for (String detail : fieldDetails.getDetails()) {
            FieldItemView itemView = new FieldItemView(this, detail, fieldName);
            fieldsGrid.addView(itemView);
        }
    }

    private MedicalFieldDetails loadMedicalFieldDetailsFromJson(@RawRes int resourceId, String fieldName) {
        InputStreamReader reader = new InputStreamReader(getResources().openRawResource(resourceId));
        Type listType = new TypeToken<List<MedicalFieldDetails>>() {}.getType();
        List<MedicalFieldDetails> fields = new Gson().fromJson(reader, listType);

        for (MedicalFieldDetails field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        return null;
    }
}
