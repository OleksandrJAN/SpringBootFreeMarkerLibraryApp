package com.spring.library.controller;

import com.spring.library.domain.User;
import com.spring.library.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class SettingsController {

    @Autowired
    private SettingsService settingsService;


    @GetMapping("/settings")
    public String getUserSettingsPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        return "user/userSettings";
    }

    @PostMapping("/settings")
    public String updateUserSettings(
            @AuthenticationPrincipal User user,
            @RequestParam("currentPasswordConfirmation") String currentPasswordConfirmation,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("newPasswordConfirmation") String newPasswordConfirmation,
            Model model
    ) {
        Map<String, Object> passwordErrorMap = settingsService.checkErrorsInPasswords(user, currentPasswordConfirmation,
                newPassword, newPasswordConfirmation);
        if (passwordErrorMap.isEmpty()) {
            settingsService.updateUserSettings(user, newPassword);
            return "redirect:/";
//            return "redirect:/logout";
        }

        model.mergeAttributes(passwordErrorMap);
        return getUserSettingsPage(user, model);
    }
}
