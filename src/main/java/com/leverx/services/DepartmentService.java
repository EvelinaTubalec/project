package com.leverx.services;

import static java.util.stream.Collectors.toList;

import com.leverx.model.Department;
import com.leverx.model.request.DepartmentRequest;
import com.leverx.model.response.DepartmentResponse;
import com.leverx.repositories.DepartmentRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentService {

  private final DepartmentRepository departmentRepository;

  public List<DepartmentResponse> findAll() {
    List<Department> departments = departmentRepository.findAll();
    return departments.stream()
        .map(
            department ->
                DepartmentResponse.builder()
                    .departmentId(department.getId())
                    .title(department.getTitle())
                    .build())
        .collect(toList());
  }

  public DepartmentResponse create(DepartmentRequest request) {
    Department department = Department.builder().title(request.getTitle()).build();

    Department savedDepartment = departmentRepository.save(department);

    return DepartmentResponse.builder()
        .departmentId(savedDepartment.getId())
        .title(savedDepartment.getTitle())
        .build();
  }

  public DepartmentResponse update(DepartmentRequest request, Long departmentId) {
    Department departmentById = departmentRepository.findDepartmentById(departmentId);
    departmentById.setTitle(request.getTitle());

    Department updatedDepartment = departmentRepository.save(departmentById);

    return DepartmentResponse.builder()
        .departmentId(departmentId)
        .title(updatedDepartment.getTitle())
        .build();
  }

  public void delete(Long departmentId) {
    departmentRepository.deleteById(departmentId);
  }
}
