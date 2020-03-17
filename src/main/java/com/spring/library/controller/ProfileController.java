package com.spring.library.controller;

import com.spring.library.domain.User;
import com.spring.library.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;


    @GetMapping
    public String getUserProfilePage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        return "profile";
    }

    @PostMapping
    public String updateUserProfile(@AuthenticationPrincipal User user,
                                    @RequestParam("currentPasswordConfirmation") String currentPasswordConfirmation,
                                    @RequestParam("newPassword") String newPassword,
                                    @RequestParam("newPasswordConfirmation") String newPasswordConfirmation,
                                    Model model
    ) {
        Map<String, Object> passwordErrorMap = profileService.checkErrorsInPasswords(user, currentPasswordConfirmation,
                newPassword, newPasswordConfirmation);
        if (passwordErrorMap.isEmpty()) {
            profileService.updateUserProfile(user, newPassword);
            return  "redirect:/";
//            return "redirect:/logout";
        }

        model.mergeAttributes(passwordErrorMap);
        return getUserProfilePage(user, model);
    }
}
