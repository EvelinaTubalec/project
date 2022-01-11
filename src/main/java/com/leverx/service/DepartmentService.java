package com.leverx.service;

import com.leverx.model.Department;
import com.leverx.model.dto.request.DepartmentRequestDto;
import com.leverx.model.dto.response.DepartmentResponseDto;
import com.leverx.model.convertor.DepartmentConvertor;
import com.leverx.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {

  private final DepartmentRepository departmentRepository;

  public List<DepartmentResponseDto> findAll() {
    List<Department> departments = departmentRepository.findAll();
    return DepartmentConvertor.toListResponse(departments);
  }

  public DepartmentResponseDto create(DepartmentRequestDto request) {
    Department department = DepartmentConvertor.toEntity(request);
    Department savedDepartment = departmentRepository.save(department);
    return DepartmentConvertor.toResponse(savedDepartment);
  }

  public DepartmentResponseDto update(DepartmentRequestDto request, Long departmentId) {
    Department departmentById = departmentRepository.findById(departmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Department with id = " + departmentId + " doesn't exists"));
    Department department = DepartmentConvertor.toEntity(request, departmentById);
    Department updatedDepartment = departmentRepository.save(department);
    return DepartmentConvertor.toResponse(updatedDepartment);
  }

  public void delete(Long departmentId) {
    departmentRepository.findById(departmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Department with id = " + departmentId + " doesn't exists"));
    departmentRepository.deleteById(departmentId);
  }
}
