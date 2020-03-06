package com.spring.library.Controller;

import com.spring.library.domain.User;
import com.spring.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;


    @GetMapping
    public String getUserProfilePage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        return "profile";
    }

    // добавить логику(возиожно добавить переименование для пользователя)
    // redirect:/profile
    @PostMapping
    public String updateUserProfile(@AuthenticationPrincipal User user, @RequestParam String password) {
        userService.updateUserProfile(user, password);
        return "redirect:/messages";
    }
}
