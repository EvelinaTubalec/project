package com.leverx.repositories;

import com.leverx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findUserById(Long id);
}
