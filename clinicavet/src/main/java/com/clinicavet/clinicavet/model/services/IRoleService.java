package com.clinicavet.clinicavet.model.services;

import com.clinicavet.clinicavet.model.entities.Role;
import java.util.List;

public interface IRoleService {
    List<Role> findAll();
    Role findById(int id);
    Role findByName(String name);
    void save(Role role);
    void delete(int id);
}
