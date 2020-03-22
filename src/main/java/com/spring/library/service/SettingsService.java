package com.spring.library.service;

import com.spring.library.domain.User;
import com.spring.library.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

// individual .properties?? (ex settingsError.properties)

@Service
public class SettingsService {
    private static final String CURRENT_PASSWORD_CONFIRMATION_INPUT_NAME = "currentPasswordConfirmation";
    private static final String NEW_PASSWORD_INPUT_NAME = "newPassword";
    private static final String NEW_PASSWORD_CONFIRMATION_INPUT_NAME = "newPasswordConfirmation";

    private static final String CURR_PASSWORD_EMPTY_ERROR = "Password confirmation cannot be empty";
    private static final String CURR_PASSWORD_CONFIRM_ERROR = "Password confirmation is incorrect";
    private static final String NEW_PASSWORD_EMPTY_ERROR = "New password cannot be empty";
    private static final String NEW_PASSWORD_CONFIRMATION_EMPTY_ERROR = "New password confirmation cannot be empty";
    private static final String NEW_PASSWORDS_CONFIRM_ERROR = "Passwords are different";


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Map<String, Object> checkErrorsInPasswords(User currentUser, Map<String, String> form) {
        Map<String, Object> passwordErrorsMap = new HashMap<>();

        String currentPasswordConfirmation = form.get(CURRENT_PASSWORD_CONFIRMATION_INPUT_NAME);
        if (StringUtils.isEmpty(currentPasswordConfirmation)) {
            passwordErrorsMap.put(CURRENT_PASSWORD_CONFIRMATION_INPUT_NAME + "Error", CURR_PASSWORD_EMPTY_ERROR);
        } else {
            boolean passwordsMatches = passwordEncoder.matches(currentPasswordConfirmation, currentUser.getPassword());
            if (!passwordsMatches) {
                passwordErrorsMap.put(CURRENT_PASSWORD_CONFIRMATION_INPUT_NAME + "Error", CURR_PASSWORD_CONFIRM_ERROR);
            }
        }

        String newPassword = form.get(NEW_PASSWORD_INPUT_NAME);
        boolean isEmptyNewPassword = StringUtils.isEmpty(newPassword);
        if (isEmptyNewPassword) {
            passwordErrorsMap.put(NEW_PASSWORD_INPUT_NAME + "Error", NEW_PASSWORD_EMPTY_ERROR);
        }

        String newPasswordConfirmation = form.get(NEW_PASSWORD_CONFIRMATION_INPUT_NAME);
        boolean isEmptyNewPasswordConfirmation = StringUtils.isEmpty(newPasswordConfirmation);
        if (isEmptyNewPasswordConfirmation) {
            passwordErrorsMap.put(NEW_PASSWORD_CONFIRMATION_INPUT_NAME + "Error", NEW_PASSWORD_CONFIRMATION_EMPTY_ERROR);
        }

        boolean isDifferentPasswords = !isEmptyNewPassword && !isEmptyNewPasswordConfirmation &&
                !newPassword.equals(newPasswordConfirmation);
        if (isDifferentPasswords) {
            passwordErrorsMap.put(NEW_PASSWORD_INPUT_NAME + "Error", NEW_PASSWORDS_CONFIRM_ERROR);
        }

        return passwordErrorsMap;
    }


    public void updateUserSettings(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
    }
}
