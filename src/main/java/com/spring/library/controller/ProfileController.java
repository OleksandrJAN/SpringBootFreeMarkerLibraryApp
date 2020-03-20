package com.spring.library.controller;

import com.spring.library.domain.Role;
import com.spring.library.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ProfileController {

    @GetMapping("/users/{user:[\\d]+}")
    public String getUserProfilePage(@PathVariable User user, Model model) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
        }

        model.addAttribute("userProfile", user);
        model.addAttribute("roles", Role.values());
        return "user/userProfile";
    }

}
