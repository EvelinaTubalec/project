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
        .userId(employee.getId())
        .firstName(employee.getFirstName())
        .lastName(employee.getLastName())
        .position(employee.getJobTitle())
        .departmentId(employee.getDepartment().getId())
        .build();
  }

  public static Employee toEntity(EmployeeRequestDto request, Department department) {
    return Employee.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .password(request.getPassword())
        .jobTitle(request.getPosition())
        .department(department)
        .build();
  }

  public static Employee toEntity(EmployeeRequestDto request, Employee employee, Department department) {
    employee.setFirstName(request.getFirstName());
    employee.setLastName(request.getLastName());
    employee.setEmail(request.getEmail());
    employee.setPassword(request.getPassword());
    employee.setJobTitle(request.getPosition());
    employee.setDepartment(department);
    return employee;
  }

  public static List<EmployeeResponseDto> convertToListUserResponse(List<Employee> employees) {
    return employees.stream()
        .map(
            user ->
                EmployeeResponseDto.builder()
                    .userId(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .position(user.getJobTitle())
                    .departmentId(user.getDepartment().getId())
                    .build())
        .collect(Collectors.toList());
  }
}
