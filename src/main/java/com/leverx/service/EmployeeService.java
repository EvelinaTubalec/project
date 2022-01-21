package com.leverx.service;

import com.leverx.model.Department;
import com.leverx.model.Employee;
import com.leverx.model.convertor.AvailableUserConvertor;
import com.leverx.model.dto.request.DepartmentRequestDto;
import com.leverx.model.dto.request.EmployeeRequestDto;
import com.leverx.model.dto.response.AvailableEmployeeResponseDto;
import com.leverx.model.dto.response.EmployeeResponseDto;
import com.leverx.model.convertor.EmployeeConvertor;
import com.leverx.repository.DepartmentRepository;
import com.leverx.repository.EmployeeRepository;
import com.leverx.repository.ProjectPositionRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final DepartmentRepository departmentRepository;
  private final ProjectPositionRepository projectPositionRepository;

  public List<EmployeeResponseDto> findAll() {
    List<Employee> employees = employeeRepository.findAll();
    return EmployeeConvertor.convertToListEmployeeResponse(employees);
  }

  public EmployeeResponseDto create(EmployeeRequestDto request) {
    validateEmployeeRequest(request);

    Department departmentById = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Department with id = " + request.getDepartmentId() + " doesn't exists"));
    Employee employee = EmployeeConvertor.toEntity(request, departmentById);
    Employee savedEmployee = employeeRepository.save(employee);
    return EmployeeConvertor.toResponse(savedEmployee);
  }

  private void validateEmployeeRequest(EmployeeRequestDto request) {
    if(StringUtils.isEmpty(request.getFirstName()) ||
            StringUtils.isEmpty(request.getLastName()) ||
            StringUtils.isEmpty(request.getEmail()) ||
            StringUtils.isEmpty(request.getPassword()) ||
            StringUtils.isEmpty(request.getJobTitle()) ||
            (request.getDepartmentId() == null)){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "Field(s) is empty");
    }
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

  public List<AvailableEmployeeResponseDto> findListOfAvailableEmployees(LocalDate date){
    List<Long> allEmployeeId = projectPositionRepository.findAvailableEmployee(date);
    List<Employee> employees = new ArrayList<>();
    for (Long employeeId : allEmployeeId){
      Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "Employee with id = " + employeeId + " doesn't exists"));
      employees.add(employee);
    }
    List<AvailableEmployeeResponseDto> response = new ArrayList<>();
    for (Employee employee : employees) {
      LocalDate availableTo = projectPositionRepository.findAvailableDateOfEmployee(employee.getId(), date);
      LocalDate availableFrom = projectPositionRepository.findLastProjectPositionDateOfEmployee(employee.getId(), date);
      AvailableEmployeeResponseDto availableEmployeeResponseDto = AvailableUserConvertor.toResponse(employee, availableTo, availableFrom);
      response.add(availableEmployeeResponseDto);
    }
    return response;
  }
}
