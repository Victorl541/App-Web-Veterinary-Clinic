package com.clinicavet.clinicavet.controllers;

import com.clinicavet.clinicavet.model.entities.Appointment;
import com.clinicavet.clinicavet.model.entities.User;
import com.clinicavet.clinicavet.model.services.IAppointmentService;
import com.clinicavet.clinicavet.model.services.IOwnerService;
import com.clinicavet.clinicavet.model.services.IPetService;
import com.clinicavet.clinicavet.model.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPetService petService;

    @Autowired
    private IOwnerService ownerService;

    @Autowired
    private IAppointmentService appointmentService;

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

        int totalMascotas = petService.findAll().size();
        int totalDuenios = ownerService.findAll().size();
        int citasHoy = appointmentService.findByDate(LocalDate.now()).size();
        int citasPendientes = appointmentService.findByStatus(Appointment.Status.pendiente).size();

        model.addAttribute("totalMascotas", totalMascotas);
        model.addAttribute("totalDuenios", totalDuenios);
        model.addAttribute("citasHoy", citasHoy);
        model.addAttribute("citasPendientes", citasPendientes);
        model.addAttribute("citasPorMes", appointmentService.countByMonth());
        model.addAttribute("mascotasPorEspecie", petService.countBySpecies());

        return "dashboard";
    }


}