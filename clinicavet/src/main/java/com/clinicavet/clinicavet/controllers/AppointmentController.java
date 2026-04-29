package com.clinicavet.clinicavet.controllers;

import com.clinicavet.clinicavet.model.entities.Appointment;
import com.clinicavet.clinicavet.model.entities.Owner;
import com.clinicavet.clinicavet.model.entities.Pet;
import com.clinicavet.clinicavet.model.entities.User;
import com.clinicavet.clinicavet.model.services.IAppointmentService;
import com.clinicavet.clinicavet.model.services.IOwnerService;
import com.clinicavet.clinicavet.model.services.IPetService;
import com.clinicavet.clinicavet.model.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/citas")
public class AppointmentController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IOwnerService ownerService;

    @Autowired
    private IPetService petService;

    @Autowired
    private IAppointmentService appointmentService;

    /**
     * Mostrar página de citas
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    public String citasPage(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("username", user.getName());
        model.addAttribute("role", user.getRole().getName());

        String initials = Arrays.stream(user.getName().split(" "))
                .limit(2)
                .map(s -> String.valueOf(s.charAt(0)).toUpperCase())
                .collect(Collectors.joining());
        model.addAttribute("initials", initials);

        // Obtener médicos (usuarios con rol de médico)
        List<User> medicos = userService.findAll().stream()
                .filter(u -> u.isActive() && u.getRole() != null && u.getRole().getName().equals("medico"))
                .collect(Collectors.toList());
        model.addAttribute("medicos", medicos);

        return "citas";
    }

    /**
     * Endpoint para buscar propietarios por nombre o cédula
     * Retorna JSON con los propietarios encontrados
     */
    @GetMapping("/propietarios")
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> buscarPropietarios(@RequestParam String search) {
        List<Owner> owners = ownerService.findAll();

        List<Map<String, Object>> resultado = owners.stream()
                .filter(owner -> owner.isActive() && (
                        owner.getName().toLowerCase().contains(search.toLowerCase()) ||
                        owner.getCedula().toLowerCase().contains(search.toLowerCase())
                ))
                .map(owner -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("id", owner.getId());
                    map.put("nombre", owner.getName());
                    map.put("cedula", owner.getCedula());
                    map.put("email", owner.getEmail());
                    map.put("direccion", owner.getAddress());
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }

    /**
     * Endpoint para obtener las mascotas activas de un propietario
     * Retorna JSON con las mascotas
     */
    @GetMapping("/mascotas/{ownerId}")
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> obtenerMascotas(@PathVariable int ownerId) {
        List<Pet> mascotas = petService.findByOwnerId(ownerId).stream()
                .filter(Pet::isActive)
                .collect(Collectors.toList());

        List<Map<String, Object>> resultado = mascotas.stream()
                .map(pet -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("id", pet.getId());
                    map.put("nombre", pet.getName());
                    map.put("genero", pet.getGender());
                    map.put("edad", pet.getAge());
                    map.put("especie", pet.getBreed().getSpecies().getName());
                    map.put("raza", pet.getBreed().getName());
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }

    /**
     * Crear una nueva cita
     */
    @PostMapping("/crear")
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> crearCita(
            @RequestParam int petId,
            @RequestParam int medicoId,
            @RequestParam String fecha,
            @RequestParam String hora,
            Principal principal) {

        try {
            // Validar que la fecha no sea anterior a hoy
            LocalDateTime fechaHora = LocalDateTime.parse(fecha + "T" + hora);
            LocalDateTime ahora = LocalDateTime.now();

            if (fechaHora.isBefore(ahora)) {
                return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "No se puede agendar citas en fechas anteriores al día actual")
                );
            }

            // Obtener la mascota
            Pet pet = petService.findById(petId);
            if (pet == null) {
                return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Mascota no encontrada")
                );
            }

            // Obtener el médico
            User medico = userService.findById(medicoId);
            if (medico == null) {
                return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Médico no encontrado")
                );
            }

            // Crear la cita
            Appointment appointment = new Appointment();
            appointment.setPet(pet);
            appointment.setUser(medico);
            appointment.setDate(fechaHora);
            appointment.setStatus(Appointment.Status.pendiente);

            appointmentService.save(appointment);

            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Cita agendada exitosamente",
                "appointmentId", appointment.getId()
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("success", false, "message", "Error al agendar la cita: " + e.getMessage())
            );
        }
    }

    /**
     * Obtener todas las citas en formato JSON para el calendario
     */
    @GetMapping("/calendario")
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> obtenerCitasCalendario(Principal principal) {
        User userActual = userService.findByEmail(principal.getName());
        List<Appointment> appointments = appointmentService.findAll();

        // Si es médico solo ve sus citas
        if (userActual.getRole().getName().equals("medico")) {
            appointments = appointments.stream()
                    .filter(a -> a.getUser().getId() == userActual.getId())
                    .collect(Collectors.toList());
        }

        List<Map<String, Object>> eventos = appointments.stream()
                .map(apt -> {
                    Map<String, Object> event = new LinkedHashMap<>();
                    event.put("id", apt.getId());
                    event.put("title", apt.getPet().getName() + " - " + apt.getUser().getName());
                    event.put("start", apt.getDate().toString());
                    event.put("backgroundColor", getColorByStatus(apt.getStatus()));
                    event.put("borderColor", getColorByStatus(apt.getStatus()));
                    event.put("extendedProps", Map.of(
                            "petName", apt.getPet().getName(),
                            "ownerName", apt.getPet().getOwner().getName(),
                            "doctorName", apt.getUser().getName(),
                            "status", apt.getStatus().toString()
                    ));
                    return event;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(eventos);
    }

    /**
     * Cancelar una cita por ID
     */
    @PostMapping("/cancelar/{appointmentId}")
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> cancelarCita(@PathVariable int appointmentId) {
        try {
            // Obtener la cita
            Appointment appointment = appointmentService.findById(appointmentId);
            if (appointment == null) {
                return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Cita no encontrada")
                );
            }

            // Cambiar el estado a cancelada
            appointment.setStatus(Appointment.Status.cancelada);
            appointmentService.save(appointment);

            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Cita cancelada exitosamente"
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("success", false, "message", "Error al cancelar la cita: " + e.getMessage())
            );
        }
    }

    // ...existing code...
    private String getColorByStatus(Appointment.Status status) {
        return switch (status) {
            case pendiente -> "#FFB800";
            case en_curso -> "#4A6CF7";
            case completada -> "#2FBF71";
            case cancelada -> "#FF6B6B";
        };
    }
}

