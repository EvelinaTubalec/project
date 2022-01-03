package com.leverx.repositories;

import com.leverx.model.Department;
import com.leverx.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

    Department findDepartmentById(Long id);
}
