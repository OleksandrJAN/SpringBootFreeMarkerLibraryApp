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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getUserList(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "userList";
    }

    @GetMapping("{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getUserEditPage(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }


    @PostMapping("{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUserRoles(
            @PathVariable User user,
            @RequestParam Map<String, String> form,
            Model model
    ) {
        Set<Role> selectedRoles = userService.getSelectedRolesFromForm(form);
        if (selectedRoles.isEmpty()) {
            model.addAttribute("rolesError", "User roles cannot be empty");
            return getUserEditPage(user, model);
        }

        userService.updateUserRoles(user, selectedRoles);
        return "redirect:/users";
    }

}
