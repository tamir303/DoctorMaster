package com.example.doctormaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctormaster.R;
import com.example.doctormaster.activities.fragments.MenuFragment;
import com.example.doctormaster.firebase.Authenticate;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, doctorUidEditText;
    private CheckBox rememberMeCheckBox;
    private TextView forgotPasswordTextView;
    private Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        doctorUidEditText = findViewById(R.id.doctorUidEditText);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        String userType = getIntent().getStringExtra("USER_TYPE"); // "patient" or "doctor"

        if (userType.equals("patient")) {
            emailEditText.setVisibility(View.VISIBLE);
            passwordEditText.setVisibility(View.VISIBLE);
            rememberMeCheckBox.setVisibility(View.VISIBLE);
            forgotPasswordTextView.setVisibility(View.VISIBLE);
            doctorUidEditText.setVisibility(View.GONE);

            loginButton.setOnClickListener(view -> {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                Authenticate.loginUser(email, password, LoginActivity.this, MedicalFieldDetailsActivity.class);
            });

            registerButton.setOnClickListener(view -> {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                Authenticate.registerUser(email, password, LoginActivity.this, MedicalFieldDetailsActivity.class);
            });
        } else {
            emailEditText.setVisibility(View.GONE);
            passwordEditText.setVisibility(View.GONE);
            rememberMeCheckBox.setVisibility(View.GONE);
            forgotPasswordTextView.setVisibility(View.GONE);
            registerButton.setVisibility(View.GONE);
            doctorUidEditText.setVisibility(View.VISIBLE);

            loginButton.setOnClickListener(view -> {
                String uid = doctorUidEditText.getText().toString().trim();
                Intent intent = new Intent(LoginActivity.this, EditDoctorProfileActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            });
        }
    }
}
