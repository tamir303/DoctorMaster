package com.example.doctormaster;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctormaster.models.MedicalFieldDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class MedicalFieldActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_medicalfield);

        Intent intent = getIntent();
        String fieldName = intent.getStringExtra("name");
        int iconResId = intent.getIntExtra("iconResId", -1);

        ImageView headerIcon = findViewById(R.id.headerIcon);
        TextView headerTitle = findViewById(R.id.headerTitle);
        GridLayout fieldsGrid = findViewById(R.id.gridLayout);

        if (iconResId != -1) {
            headerIcon.setImageResource(iconResId);
        }
        headerTitle.setText(fieldName);

        MedicalFieldDetails fieldDetails = loadMedicalFieldDetailsFromJson(R.raw.medical_fields_details, fieldName);

        assert fieldDetails != null;

        for (String detail : fieldDetails.getDetails()) {
            TextView itemView = new TextView(this);
            itemView.setText(detail);
            itemView.setTextColor(Color.WHITE);  // Set the text color to white
            itemView.setBackgroundColor(Color.parseColor("#8DB6CD"));  // Set the background color to a darker blue
            itemView.setPadding(32, 24, 32, 24);  // Increase padding for larger size
            itemView.setGravity(Gravity.CENTER);  // Center the text inside the square

            // Define layout parameters
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;  // Make the width stretch across the available space
            params.height = 100;  // Set a fixed height to make the squares larger
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);  // Stretch horizontally
            params.setMargins(8, 8, 8, 8);  // Add margins between items
            itemView.setLayoutParams(params);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newIntent = new Intent(MedicalFieldActivity.this, DoctorDetailActivity.class);
                    intent.putExtra("speciality", detail);
                    intent.putExtra("field", "yes");
                    startActivity(newIntent);
                }
            });

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

        return null; // Handle this case as needed
    }
}
