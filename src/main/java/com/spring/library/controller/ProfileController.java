package com.spring.library.controller;

import com.spring.library.domain.Role;
import com.spring.library.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    @GetMapping("/users/{user:[\\d]+}")
    public String getUserProfilePage(@PathVariable("user") User userProfile, Model model) {
        ControllerUtils.isUserProfileExists(userProfile);

        model.addAttribute("userProfile", userProfile);
        model.addAttribute("roles", Role.values());
        return "user/userProfile";
    }

}
