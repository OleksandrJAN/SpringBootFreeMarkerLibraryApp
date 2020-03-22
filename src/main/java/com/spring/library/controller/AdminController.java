package com.spring.library.controller;

import com.spring.library.domain.Role;
import com.spring.library.domain.User;
import com.spring.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;


@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public String getUserList(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "user/userList";
    }

    @PostMapping("/users/{user:[\\d]+}/roles")
    public String updateUserRoles(
            @PathVariable("user") User userProfile,
            @RequestParam Map<String, String> form
    ) {
        ControllerUtils.isUserProfileExists(userProfile);

        Set<Role> selectedRoles = userService.getSelectedRolesFromForm(form);
        if (selectedRoles.isEmpty()) {
            selectedRoles.add(Role.USER);
        }

        userService.updateUserRoles(userProfile, selectedRoles);

        return "redirect:/users/{user}";
    }

    @DeleteMapping("/users/{user:[\\d]+}")
    public String deleteUser(@PathVariable("user") User userProfile) {
        ControllerUtils.isUserProfileExists(userProfile);

        userService.deleteUser(userProfile);

        return "redirect:/users";
    }

}
