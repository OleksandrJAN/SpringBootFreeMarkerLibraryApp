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


    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUserRoles(
            @RequestParam("userId") User user,
            @RequestParam Map<String, String> form
    ) {
        userService.updateUserRoles(user, form);

        return "redirect:/users";
    }

}
