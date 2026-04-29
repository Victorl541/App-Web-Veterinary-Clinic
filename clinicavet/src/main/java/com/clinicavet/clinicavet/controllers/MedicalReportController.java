package com.clinicavet.clinicavet.controllers;

import com.clinicavet.clinicavet.model.entities.Diagnosis;
import com.clinicavet.clinicavet.model.entities.MedicalReport;
import com.clinicavet.clinicavet.model.entities.User;
import com.clinicavet.clinicavet.model.services.IMedicalReportService;
import com.clinicavet.clinicavet.model.services.IOwnerService;
import com.clinicavet.clinicavet.model.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MedicalReportController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IOwnerService ownerService;

    @Autowired
    private IMedicalReportService medicalReportService;

    @GetMapping("/informes")
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    public String informes(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("username", user.getName());
        model.addAttribute("role", user.getRole().getName());
        String initials = Arrays.stream(user.getName().split(" "))
                .limit(2)
                .map(s -> String.valueOf(s.charAt(0)).toUpperCase())
                .collect(Collectors.joining());
        model.addAttribute("initials", initials);
        model.addAttribute("owners", ownerService.findAll());
        return "informes";
    }

    @GetMapping("/informes/historial/{idMascota}")
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    @ResponseBody
    public List<Map<String, Object>> getHistorial(@PathVariable int idMascota) {
        List<MedicalReport> informes = medicalReportService.findByPetId(idMascota);
        return informes.stream().map(i -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("fecha", i.getAppointment().getDate().toString());
            map.put("medico", i.getUser().getName());
            map.put("peso", i.getWeight());
            map.put("anamnesis", i.getAnamnesis());
            map.put("diagnosticos", i.getDiagnoses().stream()
                    .map(Diagnosis::getName)
                    .collect(Collectors.joining(", ")));
            map.put("observaciones", i.getObservations());
            map.put("procedimiento", i.getProcedure());
            return map;
        }).collect(Collectors.toList());
    }
}