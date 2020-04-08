package com.spring.library.service;

import com.spring.library.domain.Role;
import com.spring.library.domain.User;
import com.spring.library.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }

        return user;
    }


    public List<User> getUserList() {
        return userRepo.findAll();
    }

    public Set<Role> getSelectedRolesFromForm(Map<String, String> form) {
        Set<String> allRolesName = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        Set<Role> selectedRoles = new HashSet<>();
        for (String roleName : allRolesName) {
            if (form.containsKey(roleName)) {
                selectedRoles.add(Role.valueOf(roleName));
            }
        }

        return selectedRoles;
    }

    public void updateUserRoles(User user, Set<Role> roles) {
        user.getRoles().clear();
        user.getRoles().addAll(roles);
        userRepo.save(user);
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }

}
