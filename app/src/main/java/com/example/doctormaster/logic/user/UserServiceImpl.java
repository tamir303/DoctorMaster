package com.example.doctormaster.logic.user;

import static com.example.doctormaster.firebase.FirebaseOperations.getAuth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctormaster.activities.MedicalFieldDetailsActivity;
import com.example.doctormaster.firebase.FirebaseOperations;
import com.example.doctormaster.firebase.database.SecurePreferences;
import com.example.doctormaster.utils.Utils;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class UserServiceImpl implements UserService {
    private final Context context;
    private final SecurePreferences securePreferences;

    public UserServiceImpl(Context context) {
        this.context = context;
        this.securePreferences = new SecurePreferences(context);
    }

    @Override
    public void resetUserPassword() {
        showResetPasswordDialog((actionResult, userActionRequest) -> {
            if (actionResult) {
                Log.d("UserService", userActionRequest.toString());

                FirebaseOperations.getAuth()
                        .signInWithEmailAndPassword(userActionRequest.getEmail(), userActionRequest.getOldPassword())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = getAuth().getCurrentUser();
                                if (user != null) {
                                    user.updatePassword(userActionRequest.getNewPassword());
                                    Log.d("UserService", "New password as been set!");
                                    Utils.showToast((AppCompatActivity) context, "Password reset successful!");
                                    Utils.navigateToNextActivity((AppCompatActivity) context, MedicalFieldDetailsActivity.class);
                                } else
                                    Log.e("UserService", "User isn't authenticated!");
                            } else
                                Log.e("UserService", Objects.requireNonNull(task.getException().getLocalizedMessage()));
                        });
            }
        });
    }

    @Override
    public SecurePreferences getSecurePreference() {
        return this.securePreferences;
    }

    private void showResetPasswordDialog(UserCheckCallback userCheckCallback) {
        // Create a new AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Set the title for the dialog
        builder.setTitle("Reset Password");

        // Create a LinearLayout to hold the input fields
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        // Create Email input field
        final EditText emailInput = new EditText(context);
        emailInput.setHint("Enter your email");
        emailInput.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        layout.addView(emailInput);

        // Create Old Password input field
        final EditText oldPasswordInput = new EditText(context);
        oldPasswordInput.setHint("Enter your old password");
        oldPasswordInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(oldPasswordInput);

        // Create New Password input field
        final EditText newPasswordInput = new EditText(context);
        newPasswordInput.setHint("Enter your new password");
        newPasswordInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(newPasswordInput);

        // Set the layout for the dialog
        builder.setView(layout);

        // Set the positive button and its action
        builder.setPositiveButton("Reset", (dialog, which) -> {
            String email = emailInput.getText().toString();
            String oldPassword = oldPasswordInput.getText().toString();
            String newPassword = newPasswordInput.getText().toString();

            // Handle the password reset logic here
            Toast.makeText(context.getApplicationContext(), "Password reset for email: " + email, Toast.LENGTH_SHORT).show();

            userCheckCallback.onResult(true, new UserActionRequest(email, oldPassword, newPassword));
        });

        // Set the negative button and its action
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();

            userCheckCallback.onResult(false, null);

        });

        // Show the dialog
        builder.show();
    }
}
