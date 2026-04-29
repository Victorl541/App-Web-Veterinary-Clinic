package com.clinicavet.clinicavet.controllers;

import com.clinicavet.clinicavet.config.UserModelHelper;
import com.clinicavet.clinicavet.model.entities.Role;
import com.clinicavet.clinicavet.model.entities.User;
import com.clinicavet.clinicavet.model.services.IRoleService;
import com.clinicavet.clinicavet.model.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserModelHelper userModelHelper;

    /**
     * Mostrar página de usuarios
     */
    @GetMapping
    @PreAuthorize("hasRole('administrador')")
    public String usuariosPage(Model model, Principal principal) {
        userModelHelper.addUserAttributes(model, principal);
        model.addAttribute("usuarios", userService.findAll());
        model.addAttribute("roles", roleService.findAll());
        return "usuarios";
    }

    /**
     * Crear un nuevo usuario
     */
    @PostMapping("/crear")
    @PreAuthorize("hasRole('administrador')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> crearUsuario(
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String contrasena,
            @RequestParam int rolId) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            // Validar que el correo no esté en uso
            if (userService.findByEmail(correo) != null) {
                response.put("success", false);
                response.put("message", "El correo ya está registrado");
                return ResponseEntity.badRequest().body(response);
            }

            Role rol = roleService.findById(rolId);
            if (rol == null) {
                response.put("success", false);
                response.put("message", "Rol no encontrado");
                return ResponseEntity.badRequest().body(response);
            }

            User usuario = new User();
            usuario.setName(nombre);
            usuario.setEmail(correo);
            usuario.setPassword(passwordEncoder.encode(contrasena));
            usuario.setRole(rol);
            usuario.setActive(true);

            userService.save(usuario);

            response.put("success", true);
            response.put("message", "Usuario registrado exitosamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al registrar el usuario: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Editar un usuario existente (sin cambiar contraseña)
     */
    @PostMapping("/editar")
    @PreAuthorize("hasRole('administrador')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> editarUsuario(
            @RequestParam int id,
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam int rolId) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            User usuario = userService.findById(id);
            if (usuario == null) {
                response.put("success", false);
                response.put("message", "Usuario no encontrado");
                return ResponseEntity.badRequest().body(response);
            }

            // Verificar que el correo no lo use otro usuario
            User existente = userService.findByEmail(correo);
            if (existente != null && existente.getId() != id) {
                response.put("success", false);
                response.put("message", "El correo ya está en uso por otro usuario");
                return ResponseEntity.badRequest().body(response);
            }

            Role rol = roleService.findById(rolId);
            if (rol == null) {
                response.put("success", false);
                response.put("message", "Rol no encontrado");
                return ResponseEntity.badRequest().body(response);
            }

            usuario.setName(nombre);
            usuario.setEmail(correo);
            usuario.setRole(rol);

            userService.save(usuario);

            response.put("success", true);
            response.put("message", "Usuario actualizado exitosamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar el usuario: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Activar o desactivar un usuario (toggle de estado)
     */
    @PostMapping("/estado/{id}")
    @PreAuthorize("hasRole('administrador')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> cambiarEstado(@PathVariable int id) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            User usuario = userService.findById(id);
            if (usuario == null) {
                response.put("success", false);
                response.put("message", "Usuario no encontrado");
                return ResponseEntity.badRequest().body(response);
            }

            boolean nuevoEstado = !usuario.isActive();
            usuario.setActive(nuevoEstado);
            userService.save(usuario);

            String mensaje = nuevoEstado ? "Usuario activado exitosamente" : "Usuario desactivado exitosamente";
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

