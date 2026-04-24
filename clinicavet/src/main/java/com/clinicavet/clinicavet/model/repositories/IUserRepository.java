package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.User;
import java.util.List;

public interface IUserRepository {
    User findByEmail(String email);
    List<User> findByActiveTrue();
    User findByEmailAndActiveTrue(String email);
    List<User> findByRoleId(int roleId);
}
