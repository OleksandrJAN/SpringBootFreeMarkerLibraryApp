package com.spring.library.controller;

import com.spring.library.domain.Role;
import com.spring.library.domain.User;
import com.spring.library.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users/{user}")
public class ProfileController {

    @Autowired
    private ReviewService reviewService;


    @GetMapping
    public String getUserProfilePage(@PathVariable User user, Model model) {
        model.addAttribute("userProfile", user);
        model.addAttribute("roles", Role.values());
        return "userProfile";
    }

}
