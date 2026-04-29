package com.clinicavet.clinicavet.controllers;

import com.clinicavet.clinicavet.config.UserModelHelper;
import com.clinicavet.clinicavet.model.entities.*;
import com.clinicavet.clinicavet.model.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private IPetService petService;

    @Autowired
    private IBreedService breedService;

    @Autowired
    private IOwnerService ownerService;

    @Autowired
    private IVaccineService vaccineService;

    @Autowired
    private IAllergyService allergyService;

    @Autowired
    private UserModelHelper userModelHelper;

    /**
     * Mostrar página de mascotas
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    public String mascotasPage(Model model, Principal principal) {
        userModelHelper.addUserAttributes(model, principal);
        model.addAttribute("pets",      petService.findAll());
        model.addAttribute("breeds",    breedService.findAll());
        model.addAttribute("vaccines",  vaccineService.findAll());
        model.addAttribute("allergies", allergyService.findAll());

        // DTO plano para evitar StackOverflow por referencias circulares Owner <-> Pet
        List<Map<String, Object>> ownerDtos = ownerService.findAll().stream()
                .filter(Owner::isActive)
                .map(o -> {
                    Map<String, Object> dto = new java.util.LinkedHashMap<>();
                    dto.put("id",     o.getId());
                    dto.put("name",   o.getName());
                    dto.put("cedula", o.getCedula() != null ? o.getCedula() : "");
                    dto.put("active", o.isActive());
                    return dto;
                })
                .collect(java.util.stream.Collectors.toList());

        model.addAttribute("owners", ownerDtos);
        return "mascotas";
    }

    /**
     * Crear una nueva mascota
     */
    @PostMapping("/crear")
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> crearMascota(
            @RequestParam String nombre,
            @RequestParam String genero,
            @RequestParam(required = false) String fechaNaci,
            @RequestParam int idRaza,
            @RequestParam int idDuenio,
            @RequestParam(required = false) List<Integer> vacunaIds,
            @RequestParam(required = false) List<Integer> alergiaIds) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Breed breed = breedService.findById(idRaza);
            if (breed == null) {
                response.put("success", false);
                response.put("message", "Raza no encontrada");
                return ResponseEntity.badRequest().body(response);
            }

            Owner owner = ownerService.findById(idDuenio);
            if (owner == null) {
                response.put("success", false);
                response.put("message", "Propietario no encontrado");
                return ResponseEntity.badRequest().body(response);
            }

            Pet pet = new Pet();
            pet.setName(nombre);
            pet.setGender(Pet.Gender.valueOf(genero));
            pet.setBirthDate(fechaNaci != null && !fechaNaci.isEmpty() ? LocalDate.parse(fechaNaci) : null);
            pet.setBreed(breed);
            pet.setOwner(owner);
            pet.setActive(true);

            if (vacunaIds != null && !vacunaIds.isEmpty()) {
                List<Vaccine> vaccines = vacunaIds.stream()
                        .map(id -> vaccineService.findById(id))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                pet.setVaccines(vaccines);
            } else {
                pet.setVaccines(new ArrayList<>());
            }

            if (alergiaIds != null && !alergiaIds.isEmpty()) {
                List<Allergy> allergies = alergiaIds.stream()
                        .map(id -> allergyService.findById(id))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                pet.setAllergies(allergies);
            } else {
                pet.setAllergies(new ArrayList<>());
            }

            petService.save(pet);

            response.put("success", true);
            response.put("message", "Mascota registrada exitosamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al registrar la mascota: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Editar una mascota existente
     */
    @PostMapping("/editar")
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> editarMascota(
            @RequestParam int id,
            @RequestParam String nombre,
            @RequestParam String genero,
            @RequestParam(required = false) String fechaNaci,
            @RequestParam int idRaza,
            @RequestParam int idDuenio,
            @RequestParam(required = false) List<Integer> vacunaIds,
            @RequestParam(required = false) List<Integer> alergiaIds) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Pet pet = petService.findById(id);
            if (pet == null) {
                response.put("success", false);
                response.put("message", "Mascota no encontrada");
                return ResponseEntity.badRequest().body(response);
            }

            Breed breed = breedService.findById(idRaza);
            if (breed == null) {
                response.put("success", false);
                response.put("message", "Raza no encontrada");
                return ResponseEntity.badRequest().body(response);
            }

            Owner owner = ownerService.findById(idDuenio);
            if (owner == null) {
                response.put("success", false);
                response.put("message", "Propietario no encontrado");
                return ResponseEntity.badRequest().body(response);
            }

            pet.setName(nombre);
            pet.setGender(Pet.Gender.valueOf(genero));
            pet.setBirthDate(fechaNaci != null && !fechaNaci.isEmpty() ? LocalDate.parse(fechaNaci) : null);
            pet.setBreed(breed);
            pet.setOwner(owner);

            if (vacunaIds != null && !vacunaIds.isEmpty()) {
                List<Vaccine> vaccines = vacunaIds.stream()
                        .map(vid -> vaccineService.findById(vid))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                pet.setVaccines(vaccines);
            } else {
                pet.setVaccines(new ArrayList<>());
            }

            if (alergiaIds != null && !alergiaIds.isEmpty()) {
                List<Allergy> allergies = alergiaIds.stream()
                        .map(aid -> allergyService.findById(aid))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                pet.setAllergies(allergies);
            } else {
                pet.setAllergies(new ArrayList<>());
            }

            petService.save(pet);

            response.put("success", true);
            response.put("message", "Mascota actualizada exitosamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar la mascota: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Activar o desactivar una mascota (toggle de estado)
     */
    @PostMapping("/estado/{id}")
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> cambiarEstado(@PathVariable int id) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Pet pet = petService.findById(id);
            if (pet == null) {
                response.put("success", false);
                response.put("message", "Mascota no encontrada");
                return ResponseEntity.badRequest().body(response);
            }

            boolean nuevoEstado = !pet.isActive();
            pet.setActive(nuevoEstado);
            petService.save(pet);

            String mensaje = nuevoEstado ? "Mascota activada exitosamente" : "Mascota desactivada exitosamente";
            response.put("success", true);
            response.put("message", mensaje);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al cambiar el estado: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}