package com.clinicavet.clinicavet.model.services.impl;

import com.clinicavet.clinicavet.model.entities.User;
import com.clinicavet.clinicavet.model.repositories.UserRepository;
import com.clinicavet.clinicavet.model.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        try {
            return userRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al obtener usuarios", e);
        }
    }

    @Override
    public User findById(int id) {
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar usuario por id", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar usuario por email", e);
        }
    }

    @Override
    public void save(User user) {
        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al guardar usuario", e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            userRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }
}