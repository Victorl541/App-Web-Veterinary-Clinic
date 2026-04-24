package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, IUserRepository {
}
