package com.spring.library.controller;

import com.spring.library.domain.User;
import com.spring.library.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;


@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;


    @GetMapping
    public String getRegistrationPage(Model model) {
        return "log/registration";
    }

    @PostMapping
    public String addUser(
            @Valid User newUser,
            BindingResult bindingResult,
            Model model,
            @RequestParam("passwordConfirmation") String passwordConfirm
    ) {
        boolean isBindingResultHasErrors = bindingResult.hasErrors();
        if (isBindingResultHasErrors) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
        }

        Map<String, Object> passwordsErrorsMap = registrationService.hasErrorsInPasswords(newUser, passwordConfirm);
        boolean isPasswordErrors = !passwordsErrorsMap.isEmpty();
        if (isPasswordErrors) {
            model.mergeAttributes(passwordsErrorsMap);
        }

        if (isPasswordErrors || isBindingResultHasErrors) {
            return getRegistrationPage(model);
        }

        if (!registrationService.registerUser(newUser)) {
            model.addAttribute("usernameError", "User exists!");
            return getRegistrationPage(model);
        }

        model.addAttribute("registrationSuccessful", true);
        return "log/login";
    }
}
