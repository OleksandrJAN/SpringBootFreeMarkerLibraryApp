package com.spring.library.service;

import com.spring.library.domain.User;
import com.spring.library.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProfileService {
    private static final String CURR_PASSWORD_EMPTY_ERROR = "Password confirmation cannot be empty";
    private static final String CURR_PASSWORD_CONFIRM_ERROR = "Password confirmation is incorrect";

    private static final String NEW_PASSWORD_EMPTY_ERROR = "New password cannot be empty";
    private static final String NEW_PASSWORD_CONFIRMATION_EMPTY_ERROR = "New password confirmation cannot be empty";
    private static final String NEW_PASSWORDS_CONFIRM_ERROR = "Passwords are different";


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Map<String, Object> checkErrorsInPasswords(User currentUser,
                                                      String currentPasswordConfirmation,
                                                      String newPassword,
                                                      String newPasswordConfirmation
    ) {
        Map<String, Object> passwordErrorsMap = new HashMap<>();

        if (StringUtils.isEmpty(currentPasswordConfirmation)) {
            passwordErrorsMap.put("currentPasswordError", CURR_PASSWORD_EMPTY_ERROR);
        } else {
            boolean passwordsMatches = passwordEncoder.matches(currentPasswordConfirmation, currentUser.getPassword());
            if (!passwordsMatches) {
                passwordErrorsMap.put("currentPasswordError", CURR_PASSWORD_CONFIRM_ERROR);
            }
        }

        boolean isEmptyNewPassword = StringUtils.isEmpty(newPassword);
        if (isEmptyNewPassword) {
            passwordErrorsMap.put("newPasswordError", NEW_PASSWORD_EMPTY_ERROR);
        }

        boolean isEmptyNewPasswordConfirmation = StringUtils.isEmpty(newPasswordConfirmation);
        if (isEmptyNewPasswordConfirmation) {
            passwordErrorsMap.put("newPasswordConfirmError", NEW_PASSWORD_CONFIRMATION_EMPTY_ERROR);
        }

        boolean isDifferentPasswords = !isEmptyNewPassword && !isEmptyNewPasswordConfirmation &&
                !newPassword.equals(newPasswordConfirmation);
        if (isDifferentPasswords) {
            passwordErrorsMap.put("newPasswordError", NEW_PASSWORDS_CONFIRM_ERROR);
        }

        return passwordErrorsMap;
    }


    public void updateUserProfile(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
    }
}
