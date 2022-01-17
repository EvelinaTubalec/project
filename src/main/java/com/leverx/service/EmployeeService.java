package com.leverx.service;

import com.leverx.model.Department;
import com.leverx.model.Employee;
import com.leverx.model.dto.request.EmployeeRequestDto;
import com.leverx.model.dto.response.EmployeeResponseDto;
import com.leverx.model.convertor.EmployeeConvertor;
import com.leverx.repository.DepartmentRepository;
import com.leverx.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final DepartmentRepository departmentRepository;

  public List<EmployeeResponseDto> findAll() {
    List<Employee> employees = employeeRepository.findAll();
    return EmployeeConvertor.convertToListUserResponse(employees);
  }

  public EmployeeResponseDto create(EmployeeRequestDto request) {
    Department departmentById = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Department with id = " + request.getDepartmentId() + " doesn't exists"));
    Employee employee = EmployeeConvertor.toEntity(request, departmentById);
    Employee savedEmployee = employeeRepository.save(employee);
    return EmployeeConvertor.toResponse(savedEmployee);
  }

  public EmployeeResponseDto update(EmployeeRequestDto request, Long userId) {
    Employee employeeById = employeeRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Employee with id = " + userId + " doesn't exists"));
    Department departmentById = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Department with id = " + request.getDepartmentId() + " doesn't exists"));
    Employee employee = EmployeeConvertor.toEntity(request, employeeById, departmentById);
    Employee updatedEmployee = employeeRepository.save(employee);
    return EmployeeConvertor.toResponse(updatedEmployee);
  }

  public void delete(Long userId) {
    employeeRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Employee with id = " + userId + " doesn't exists"));
    employeeRepository.deleteById(userId);
  }
}
