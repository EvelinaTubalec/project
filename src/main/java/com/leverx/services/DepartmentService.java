package com.leverx.services;

import com.leverx.model.Department;
import com.leverx.model.request.DepartmentRequest;
import com.leverx.model.response.DepartmentResponse;
import com.leverx.repositories.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentResponse save(DepartmentRequest request){
        Department department = Department.builder()
                .title(request.getTitle())
                .build();

        departmentRepository.save(department);

        return DepartmentResponse.builder()
                .title(request.getTitle())
                .build();
    }
}
