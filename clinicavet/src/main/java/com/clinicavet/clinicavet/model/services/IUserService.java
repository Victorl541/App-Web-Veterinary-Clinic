package com.clinicavet.clinicavet.model.services;

import com.clinicavet.clinicavet.model.entities.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    User findById(int id);
    User findByEmail(String email);
    void save(User user);
    void delete(int id);
}
