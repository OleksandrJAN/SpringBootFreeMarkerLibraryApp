package com.spring.library.controller;

import com.spring.library.domain.Role;
import com.spring.library.domain.User;
import com.spring.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "userList";
    }

    /*/users/{user}/roles?!*/
    @PostMapping("/users/{user}")
    public String updateUserRoles(
            @PathVariable User user,
            @RequestParam Map<String, String> form,
            Model model
    ) {
        Set<Role> selectedRoles = userService.getSelectedRolesFromForm(form);
        if (!selectedRoles.isEmpty()) {
            userService.updateUserRoles(user, selectedRoles);
        }

        return "redirect:/users/{user}";
    }

}
