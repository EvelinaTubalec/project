package com.leverx.services;

import com.leverx.model.Department;
import com.leverx.model.dto.requests.DepartmentRequest;
import com.leverx.model.dto.responses.DepartmentResponse;
import com.leverx.model.utils.convertors.DepartmentConvertor;
import com.leverx.repositories.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {

  private final DepartmentRepository departmentRepository;
  private final DepartmentConvertor departmentConvertor;

  public List<DepartmentResponse> findAll() {
    List<Department> departments = departmentRepository.findAll();
    return departmentConvertor.convertToListDepartmentResponse(departments);
  }

  public DepartmentResponse create(DepartmentRequest request) {
    Department department = departmentConvertor.convertRequestToDepartment(request);
    Department savedDepartment = departmentRepository.save(department);
    return departmentConvertor.convertDepartmentToResponse(savedDepartment);
  }

  public DepartmentResponse update(DepartmentRequest request, Long departmentId) {
    Department department = departmentConvertor.convertRequestToDepartment(request, departmentId);
    Department updatedDepartment = departmentRepository.save(department);
    return departmentConvertor.convertDepartmentToResponse(updatedDepartment);
  }

  public void delete(Long departmentId) {
    departmentRepository.findById(departmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Department with id = " + departmentId + " doesn't exists"));
    departmentRepository.deleteById(departmentId);
  }
}
