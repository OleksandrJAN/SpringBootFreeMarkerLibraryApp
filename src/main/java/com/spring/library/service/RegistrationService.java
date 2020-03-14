package com.spring.library.service;

import com.spring.library.domain.Role;
import com.spring.library.domain.User;
import com.spring.library.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegistrationService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Map<String, Object> hasErrorsInPasswords(User user, String passwordConfirm) {
        Map<String, Object> passwordErrorsMap = new HashMap<>();

        if (StringUtils.isEmpty(passwordConfirm)) {
            passwordErrorsMap.put("passwordConfirmationError", "Password confirmation cannot be empty");
        }

        String userPassword = user.getPassword();
        boolean isDifferentPasswords = !StringUtils.isEmpty(userPassword) && !userPassword.equals(passwordConfirm);
        if (isDifferentPasswords) {
            passwordErrorsMap.put("passwordError", "Passwords are different!");
        }

        return passwordErrorsMap;
    }

    public boolean registerUser(User newUser) {
        if (isUserExists(newUser.getUsername())) {
            return false;
        }

        newUser.setActive(true);
        newUser.setRoles(Collections.singleton(Role.USER));
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepo.save(newUser);

        return true;
    }

    private boolean isUserExists(String username) {
        User userFromDb = userRepo.findByUsername(username);
        return userFromDb != null;
    }
}
