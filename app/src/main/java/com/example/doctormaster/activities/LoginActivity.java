package com.example.doctormaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doctormaster.R;
import com.example.doctormaster.firebase.AuthRequest;
import com.example.doctormaster.firebase.Authenticate;
import com.example.doctormaster.firebase.database.SecurePreferences;
import com.example.doctormaster.logic.user.UserService;
import com.example.doctormaster.logic.user.UserServiceImpl;

public class LoginActivity extends BaseActivity {
    private EditText emailEditText, passwordEditText, doctorUidEditText;
    private CheckBox rememberMeCheckBox;
    private TextView forgotPasswordTextView;
    private Button loginButton, registerButton;
    private UserService userService;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        getLayoutInflater().inflate(R.layout.activity_login, findViewById(R.id.container));

        userType = getIntent().getStringExtra("USER_TYPE"); // "patient" or "doctor"
        userService = new UserServiceImpl(this);
        SecurePreferences securePreferences = userService.getSecurePreference();

        InitializeViews();
        setButtonListeners();
        processUserTypeViewStatus();

        if (!securePreferences.getEmail().isEmpty() && !securePreferences.getPassword().isEmpty()) {
            emailEditText.setText(securePreferences.getEmail());
            passwordEditText.setText(securePreferences.getPassword());
            rememberMeCheckBox.setChecked(true);
        }
    }

    @Override
    public void InitializeViews() {
        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        doctorUidEditText = findViewById(R.id.doctorUidEditText);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
    }

    @Override
    public void setButtonListeners() {
        if (userType == null)
            userType = "patient";

        if (userType.equals("patient")) {
            loginButton.setOnClickListener(view -> {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                AuthRequest authRequest = new AuthRequest(
                        email,
                        password,
                        LoginActivity.this,
                        MedicalFieldDetailsActivity.class,
                        userService.getSecurePreference(),
                        rememberMeCheckBox.isChecked());

                Authenticate.loginUser(authRequest);
            });

            registerButton.setOnClickListener(view -> {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                AuthRequest authRequest = new AuthRequest(
                        email,
                        password,
                        LoginActivity.this,
                        MedicalFieldDetailsActivity.class,
                        userService.getSecurePreference(),
                        rememberMeCheckBox.isChecked());

                Authenticate.registerUser(authRequest);
            });
        } else {
            loginButton.setOnClickListener(view -> {
                String uid = doctorUidEditText.getText().toString().trim();
                Intent intent = new Intent(LoginActivity.this, EditDoctorProfileActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            });
        }
    }

    private void processUserTypeViewStatus() {
        if (userType == null)
            userType = "patient";

        if (userType.equals("patient")) {
            emailEditText.setVisibility(View.VISIBLE);
            passwordEditText.setVisibility(View.VISIBLE);
            rememberMeCheckBox.setVisibility(View.VISIBLE);
            forgotPasswordTextView.setVisibility(View.VISIBLE);
            doctorUidEditText.setVisibility(View.GONE);

            forgotPasswordTextView.setOnClickListener(view -> userService.resetUserPassword());
        } else {
            emailEditText.setVisibility(View.GONE);
            passwordEditText.setVisibility(View.GONE);
            rememberMeCheckBox.setVisibility(View.GONE);
            forgotPasswordTextView.setVisibility(View.GONE);
            registerButton.setVisibility(View.GONE);
            doctorUidEditText.setVisibility(View.VISIBLE);
        }
    }
}
