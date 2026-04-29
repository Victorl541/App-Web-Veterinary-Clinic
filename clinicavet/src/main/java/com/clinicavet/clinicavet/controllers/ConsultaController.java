package com.clinicavet.clinicavet.controllers;


import com.clinicavet.clinicavet.config.UserModelHelper;
import com.clinicavet.clinicavet.model.entities.*;
import com.clinicavet.clinicavet.model.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IOwnerService ownerService;

    @Autowired
    private IPetService petService;

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IMedicalReportService medicalReportService;

    @Autowired
    private IDiagnosisService diagnosisService;

    @Autowired
    private UserModelHelper userModelHelper;

    @GetMapping
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    public String consultas(Model model, Principal principal) {
        userModelHelper.addUserAttributes(model, principal);
        model.addAttribute("owners", ownerService.findAll());
        return "consultas";
    }

    @GetMapping("/diagnosticos")
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    @ResponseBody
    public List<Map<String, Object>> buscarDiagnosticos(@RequestParam String search) {
        return diagnosisService.findAll().stream()
                .filter(d -> d.getName().toLowerCase().contains(search.toLowerCase()))
                .limit(3)
                .map(d -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("id", d.getId());
                    map.put("nombre", d.getName());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAnyRole('administrador', 'medico')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> guardarConsulta(
            @RequestParam int petId,
            @RequestParam String fecha,
            @RequestParam String motivo,
            @RequestParam BigDecimal peso,
            @RequestParam(required = false) String anamnesis,
            @RequestParam String procedimiento,
            @RequestParam(required = false) String observaciones,
            @RequestParam(required = false) String diagnosticosIds,
            Principal principal) {

        try {
            User medico = userService.findByEmail(principal.getName());
            Pet pet = petService.findById(petId);

            if (pet == null) {
                return ResponseEntity.badRequest().body(
                        Map.of("success", false, "message", "Mascota no encontrada"));
            }

            // Buscar cita pendiente de esta mascota asignada al médico logueado
            Appointment citaPendiente = appointmentService.findByPetId(petId).stream()
                    .filter(a -> a.getStatus() == Appointment.Status.pendiente
                            && a.getUser().getId() == medico.getId())
                    .findFirst()
                    .orElse(null);

            // Crear informe médico
            MedicalReport informe = new MedicalReport();
            informe.setPet(pet);
            informe.setUser(medico);
            informe.setWeight(peso);
            informe.setAnamnesis(anamnesis != null && !anamnesis.isEmpty() ? anamnesis : null);
            informe.setProcedure(procedimiento);
            informe.setObservations(observaciones != null && !observaciones.isEmpty() ? observaciones : null);

            // Asociar cita si existe
            if (citaPendiente != null) {
                informe.setAppointment(citaPendiente);
                citaPendiente.setStatus(Appointment.Status.completada);
                appointmentService.save(citaPendiente);
            }

            // Agregar diagnósticos
            if (diagnosticosIds != null && !diagnosticosIds.isEmpty()) {
                List<Diagnosis> diagnosticos = Arrays.stream(diagnosticosIds.split(","))
                        .map(id -> diagnosisService.findById(Integer.parseInt(id.trim())))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                informe.setDiagnoses(diagnosticos);
            }

            medicalReportService.save(informe);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Consulta registrada exitosamente"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Error: " + e.getMessage()));
        }
    }
}