package com.leverx.model.utils.convertors;

import com.leverx.model.Department;
import com.leverx.model.dto.requests.DepartmentRequest;
import com.leverx.model.dto.responses.DepartmentResponse;
import com.leverx.repositories.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class DepartmentConvertor {

    private final DepartmentRepository departmentRepository;

    public DepartmentResponse convertDepartmentToResponse(Department department){
        return DepartmentResponse.builder()
                .departmentId(department.getId())
                .title(department.getTitle())
                .build();
    }

    public Department convertRequestToDepartment(DepartmentRequest request){
        return Department
                .builder()
                .title(request.getTitle())
                .build();
    }

    public Department convertRequestToDepartment(DepartmentRequest request, Long departmentId){
        Department departmentById = departmentRepository.findById(departmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Department with id = " + departmentId + " doesn't exists"));
        departmentById.setTitle(request.getTitle());
        return departmentById;
    }

    public List<DepartmentResponse> convertToListDepartmentResponse(List<Department> departments){
        return departments.stream()
                .map(
                        department ->
                                DepartmentResponse.builder()
                                        .departmentId(department.getId())
                                        .title(department.getTitle())
                                        .build())
                .collect(toList());
    }
}
