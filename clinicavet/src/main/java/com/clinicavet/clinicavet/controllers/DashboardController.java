package com.clinicavet.clinicavet.controllers;

import com.clinicavet.clinicavet.model.entities.User;
import com.clinicavet.clinicavet.model.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    private IUserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("username", user.getName());
        model.addAttribute("role", user.getRole().getName());
        String initials = Arrays.stream(user.getName().split(" "))
                .limit(2)
                .map(s -> String.valueOf(s.charAt(0)).toUpperCase())
                .collect(Collectors.joining());
        model.addAttribute("initials", initials);
        return "dashboard";
    }
}