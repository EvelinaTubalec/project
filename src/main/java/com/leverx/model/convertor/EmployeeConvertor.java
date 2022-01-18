package com.leverx.model.convertor;

import com.leverx.model.Department;
import com.leverx.model.Employee;
import com.leverx.model.dto.request.EmployeeRequestDto;
import com.leverx.model.dto.response.EmployeeResponseDto;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class EmployeeConvertor {

  public static EmployeeResponseDto toResponse(Employee employee) {
    return EmployeeResponseDto.builder()
        .employeeId(employee.getId())
        .firstName(employee.getFirstName())
        .lastName(employee.getLastName())
        .jobTitle(employee.getJobTitle())
        .departmentId(employee.getDepartment().getId())
        .build();
  }

  public static Employee toEntity(EmployeeRequestDto request, Department department) {
    return Employee.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .password(request.getPassword())
        .jobTitle(request.getJobTitle())
        .department(department)
        .build();
  }

  public static Employee toEntity(EmployeeRequestDto request, Employee employee, Department department) {
    employee.setFirstName(request.getFirstName());
    employee.setLastName(request.getLastName());
    employee.setEmail(request.getEmail());
    employee.setPassword(request.getPassword());
    employee.setJobTitle(request.getJobTitle());
    employee.setDepartment(department);
    return employee;
  }

  public static List<EmployeeResponseDto> convertToListEmployeeResponse(List<Employee> employees) {
    return employees.stream()
        .map(
            employee ->
                EmployeeResponseDto.builder()
                    .employeeId(employee.getId())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .jobTitle(employee.getJobTitle())
                    .departmentId(employee.getDepartment().getId())
                    .build())
        .collect(Collectors.toList());
  }
}
