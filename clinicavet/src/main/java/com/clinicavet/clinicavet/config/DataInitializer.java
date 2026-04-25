package com.clinicavet.clinicavet.config;

import com.clinicavet.clinicavet.model.entities.Role;
import com.clinicavet.clinicavet.model.entities.User;
import com.clinicavet.clinicavet.model.repositories.RoleRepository;
import com.clinicavet.clinicavet.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@clinica.com") == null) {
            Role adminRole = roleRepository.findByName("administrador");
            User admin = new User();
            admin.setName("Administrador");
            admin.setEmail("admin@clinica.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(adminRole);
            admin.setActive(true);
            userRepository.save(admin);
            System.out.println("Admin creado exitosamente");
        }
    }
}