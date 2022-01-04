package com.leverx.services;

import com.leverx.model.Department;
import com.leverx.model.User;
import com.leverx.model.request.DepartmentRequest;
import com.leverx.model.request.UserRequest;
import com.leverx.model.response.DepartmentResponse;
import com.leverx.model.response.UserResponse;
import com.leverx.repositories.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<DepartmentResponse> findAll(){
        List<Department> departments = (List<Department>) departmentRepository.findAll();
        List<DepartmentResponse> result = new ArrayList<>();
        for (Department d : departments) {
            DepartmentResponse departmentResponse = DepartmentResponse.builder()
                    .departmentId(d.getId())
                    .title(d.getTitle())
                    .build();
            result.add(departmentResponse);
        }
        return result;
    }

    public DepartmentResponse create(DepartmentRequest request){
        Department department = Department.builder()
                .title(request.getTitle())
                .build();

        departmentRepository.save(department);

        return DepartmentResponse.builder()
                .departmentId(department.getId())
                .title(department.getTitle())
                .build();
    }

    public DepartmentResponse update(DepartmentRequest request, Long departmentId){
        Department departmentById = departmentRepository.findDepartmentById(departmentId);
        departmentById.setTitle(request.getTitle());

        departmentRepository.save(departmentById);

        return DepartmentResponse.builder()
                .departmentId(departmentId)
                .title(departmentById.getTitle())
                .build();
    }

    public void delete(Long departmentId){
        departmentRepository.deleteById(departmentId);
    }
}
