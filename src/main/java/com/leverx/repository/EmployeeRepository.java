package com.leverx.repository;

import com.leverx.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM employee ORDER BY id ASC ", nativeQuery = true)
    List<Employee> findAllAsc();
}
