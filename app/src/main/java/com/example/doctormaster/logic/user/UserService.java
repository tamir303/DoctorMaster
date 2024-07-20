package com.example.doctormaster.logic.user;

import com.example.doctormaster.firebase.database.SecurePreferences;

public interface UserService {
    void resetUserPassword();
    SecurePreferences getSecurePreference();
}
