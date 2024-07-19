package com.example.doctormaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doctormaster.R;
import com.example.doctormaster.adapter.SpecialityAdapter;
import com.example.doctormaster.firebase.FirestoreCallback;
import com.example.doctormaster.firebase.database.DoctorDB;
import com.example.doctormaster.models.Doctor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditDoctorProfileActivity extends AppCompatActivity {

    private Spinner spinnerField;
    private RecyclerView recyclerViewSpecialties;
    private SpecialityAdapter specialityAdapter;

    private EditText etName, etLocation, etField, etDescription, etSpecialties,
            etExperience, etRating, etStartHour, etFinishHour,
            etBreakHour, etBreakLength;
    private Button btnSave;

    private final List<String> cardiologySpecialties = Arrays.asList("Cardiologist", "Interventional Cardiologist", "Angioplasty", "Heart Bypass Surgery");
    private final List<String> pregnancySpecialties = Arrays.asList("Obstetrician", "Gynecologist", "Prenatal Care", "Cesarean Section");
    private final List<String> gastroenterologistSpecialties = Arrays.asList("Gastroenterologist", "Hepatologist", "Endoscopy", "Liver Biopsy");
    private final List<String> mentalHealthSpecialties = Arrays.asList("Psychiatrist", "Psychologist", "Cognitive Behavioral Therapy", "Medication Management");
    private final List<String> orthopedicsSpecialties = Arrays.asList("Orthopedic Surgeon", "Rheumatologist", "Joint Replacement", "Arthroscopy");
    private final List<String> neurosurgerySpecialties = Arrays.asList("Neurosurgeon", "Spine Surgeon", "Brain Tumor Surgery", "Spinal Fusion");
    private final List<String> emergencySpecialties = Arrays.asList("Emergency Physician", "Trauma Surgeon", "CPR", "Fracture Repair");
    private final List<String> dentistrySpecialties = Arrays.asList("Dentist", "Orthodontist", "Teeth Cleaning", "Root Canal Treatment");
    private final List<String> otolaryngologySpecialties = Arrays.asList("ENT Specialist", "Otolaryngologist", "Tonsillectomy", "Sinus Surgery");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor_profile);

        spinnerField = findViewById(R.id.spinnerField);
        recyclerViewSpecialties = findViewById(R.id.recyclerViewSpecialties);

        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");

        etName = findViewById(R.id.etName);
        etLocation = findViewById(R.id.etLocation);
        etDescription = findViewById(R.id.etDescription);
        etExperience = findViewById(R.id.etExperience);
        etRating = findViewById(R.id.etRating);
        etStartHour = findViewById(R.id.etStartHour);
        etFinishHour = findViewById(R.id.etFinishHour);
        etBreakHour = findViewById(R.id.etBreakHour);
        etBreakLength = findViewById(R.id.etBreakLength);
        btnSave = findViewById(R.id.btnSave);

        DoctorDB.findDoctorByUid(EditDoctorProfileActivity.this, uid, new FirestoreCallback<Doctor>() {
            @Override
            public void onCallBack(Doctor result) {
                if (result != null) {
                    etName.setText(result.getName());
                    etLocation.setText(result.getLocation());
                    etDescription.setText(result.getDescription());
                    etExperience.setText(result.getExperience().toString());
                    etRating.setText(result.getRating().toString());
                    etStartHour.setText(result.getStartHour().toString());
                    etFinishHour.setText(result.getFinishHour().toString());
                    etBreakHour.setText(result.getFinishHour().toString());
                    etBreakLength.setText(result.getBreakLength().toString());
                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.fields_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerField.setAdapter(adapter);

        recyclerViewSpecialties.setLayoutManager(new LinearLayoutManager(this));
        specialityAdapter = new SpecialityAdapter(new ArrayList<>());
        recyclerViewSpecialties.setAdapter(specialityAdapter);

        spinnerField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateSpecialities(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case when nothing is selected
            }
        });

        btnSave.setOnClickListener(v -> {
            Doctor doctor = new Doctor(
                    uid, // UID can be set from elsewhere
                    etName.getText().toString(),
                    etLocation.getText().toString(),
                    etField.getText().toString(),
                    etDescription.getText().toString(),
                    parseSpecialties(etSpecialties.getText().toString()),
                    parseInteger(etExperience.getText().toString()),
                    parseInteger(etRating.getText().toString()),
                    parseInteger(etStartHour.getText().toString()),
                    parseInteger(etFinishHour.getText().toString()),
                    parseInteger(etBreakHour.getText().toString()),
                    parseInteger(etBreakLength.getText().toString())
            );

            // Save the doctor object to the database or perform other actions here
        });
    }

    private void updateSpecialities(int position) {
        List<String> specialities;
        switch (position) {
            case 0:
                specialities = cardiologySpecialties;
                break;
            case 1:
                specialities = pregnancySpecialties;
                break;
            case 2:
                specialities = gastroenterologistSpecialties;
                break;
            case 3:
                specialities = mentalHealthSpecialties;
                break;
            case 4:
                specialities = orthopedicsSpecialties;
                break;
            case 5:
                specialities = neurosurgerySpecialties;
                break;
            case 6:
                specialities = emergencySpecialties;
                break;
            case 7:
                specialities = dentistrySpecialties;
                break;
            case 8:
                specialities = otolaryngologySpecialties;
                break;
            default:
                specialities = new ArrayList<>();
                break;
        }
        specialityAdapter = new SpecialityAdapter(specialities);
        recyclerViewSpecialties.setAdapter(specialityAdapter);
    }

    private List<String> parseSpecialties(String specialtiesStr) {
        return Arrays.asList(specialtiesStr.split("\\s*,\\s*"));
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
