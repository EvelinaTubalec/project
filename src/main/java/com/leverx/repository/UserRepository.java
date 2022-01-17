package com.leverx.repository;

import com.leverx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from users order by id asc ", nativeQuery = true)
    List<User> findAllAsc();
}
