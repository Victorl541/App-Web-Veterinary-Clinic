package com.clinicavet.clinicavet.controllers;

import com.clinicavet.clinicavet.config.UserModelHelper;
import com.clinicavet.clinicavet.model.entities.Owner;
import com.clinicavet.clinicavet.model.services.IOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/duenios")
public class PropietarioController {

    @Autowired
    private IOwnerService ownerService;

    @Autowired
    private UserModelHelper userModelHelper;

    /**
     * Mostrar página de propietarios
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    public String propietariosPage(Model model, Principal principal) {
        userModelHelper.addUserAttributes(model, principal);
        model.addAttribute("owners", ownerService.findAll());
        return "propietarios";
    }

    /**
     * Crear un nuevo propietario
     */
    @PostMapping("/crear")
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> crearPropietario(
            @RequestParam String nombre,
            @RequestParam String cedula,
            @RequestParam(required = false) String correo,
            @RequestParam(required = false) String direccion) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            // Verificar que la cédula no esté ya registrada
            boolean cedulaExiste = ownerService.findAll().stream()
                    .anyMatch(o -> o.getCedula().equalsIgnoreCase(cedula));

            if (cedulaExiste) {
                response.put("success", false);
                response.put("message", "Ya existe un propietario con esa cédula");
                return ResponseEntity.badRequest().body(response);
            }

            Owner owner = new Owner();
            owner.setName(nombre);
            owner.setCedula(cedula);
            owner.setEmail(correo != null && !correo.isEmpty() ? correo : null);
            owner.setAddress(direccion != null && !direccion.isEmpty() ? direccion : null);
            owner.setActive(true);

            ownerService.save(owner);

            response.put("success", true);
            response.put("message", "Propietario registrado exitosamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al registrar el propietario: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Editar un propietario existente
     */
    @PostMapping("/editar")
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> editarPropietario(
            @RequestParam int id,
            @RequestParam String nombre,
            @RequestParam String cedula,
            @RequestParam(required = false) String correo,
            @RequestParam(required = false) String direccion) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Owner owner = ownerService.findById(id);
            if (owner == null) {
                response.put("success", false);
                response.put("message", "Propietario no encontrado");
                return ResponseEntity.badRequest().body(response);
            }

            // Verificar que la cédula no la use otro propietario
            boolean cedulaExiste = ownerService.findAll().stream()
                    .anyMatch(o -> o.getCedula().equalsIgnoreCase(cedula) && o.getId() != id);

            if (cedulaExiste) {
                response.put("success", false);
                response.put("message", "Ya existe otro propietario con esa cédula");
                return ResponseEntity.badRequest().body(response);
            }

            owner.setName(nombre);
            owner.setCedula(cedula);
            owner.setEmail(correo != null && !correo.isEmpty() ? correo : null);
            owner.setAddress(direccion != null && !direccion.isEmpty() ? direccion : null);

            ownerService.save(owner);

            response.put("success", true);
            response.put("message", "Propietario actualizado exitosamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar el propietario: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Activar o desactivar un propietario (toggle de estado)
     */
    @PostMapping("/estado/{id}")
    @PreAuthorize("hasAnyRole('administrador', 'medico', 'auxiliar')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> cambiarEstado(@PathVariable int id) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Owner owner = ownerService.findById(id);
            if (owner == null) {
                response.put("success", false);
                response.put("message", "Propietario no encontrado");
                return ResponseEntity.badRequest().body(response);
            }

            boolean nuevoEstado = !owner.isActive();
            owner.setActive(nuevoEstado);
            ownerService.save(owner);

            String mensaje = nuevoEstado ? "Propietario activado exitosamente" : "Propietario desactivado exitosamente";
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

