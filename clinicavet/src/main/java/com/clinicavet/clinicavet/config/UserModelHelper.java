package com.clinicavet.clinicavet.config;

import org.springframework.ui.Model;
import com.clinicavet.clinicavet.model.entities.User;
import com.clinicavet.clinicavet.model.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class UserModelHelper {

    @Autowired
    private IUserService userService;

    public void addUserAttributes(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("username", user.getName());
        model.addAttribute("role", user.getRole().getName());
        String initials = Arrays.stream(user.getName().split(" "))
                .limit(2)
                .map(s -> String.valueOf(s.charAt(0)).toUpperCase())
                .collect(Collectors.joining());
        model.addAttribute("initials", initials);
    }
}