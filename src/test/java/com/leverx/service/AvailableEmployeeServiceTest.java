package com.leverx.service;

import com.leverx.model.Department;
import com.leverx.model.Employee;
import com.leverx.model.convertor.AvailableUserConvertor;
import com.leverx.model.dto.response.AvailableEmployeeResponseDto;
import com.leverx.repository.DepartmentRepository;
import com.leverx.repository.EmployeeRepository;
import com.leverx.repository.ProjectPositionRepository;
import liquibase.pro.packaged.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AvailableEmployeeServiceTest {

    @Mock
    ProjectPositionRepository projectPositionRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    AvailableEmployeeService availableEmployeeService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findListOfAvailableUsers() {
        LocalDate date = LocalDate.now();
        Department department = new Department(1L, "title");
        Employee employee = new Employee(1L, "firstName", "listName", "email", "password", "jobTitle", department);

        List<Long> availableEmployees = new ArrayList<>();
        availableEmployees.add(1L);
        when(projectPositionRepository.findAvailableEmployee(date)).thenReturn(availableEmployees);

        List<Employee> listOfAvailableEmployees = new ArrayList<>();
        for (Long employeeId : availableEmployees){
            when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
            listOfAvailableEmployees.add(employee);
        }

        List<AvailableEmployeeResponseDto> expectedResponse = new ArrayList<>();
        for (Employee empl : listOfAvailableEmployees) {
            LocalDate availableToDateOfEmployee = LocalDate.of(2022, 1, 1);
            LocalDate availableFromDateOfEmployees = LocalDate.of(2022, 2, 25);
            when(projectPositionRepository.findAvailableDateOfEmployee(empl.getId(), date)).thenReturn(availableToDateOfEmployee);
            when(projectPositionRepository.findLastProjectPositionDateOfEmployee(empl.getId(), date)).thenReturn(availableFromDateOfEmployees);

            AvailableEmployeeResponseDto availableEmployeeResponseDto = AvailableUserConvertor.toResponse(empl, availableToDateOfEmployee, availableFromDateOfEmployees);
            expectedResponse.add(availableEmployeeResponseDto);
        }

        List<AvailableEmployeeResponseDto> actualResponse = availableEmployeeService.findListOfAvailableEmployees(LocalDate.now());

        Assertions.assertEquals(expectedResponse.size(), actualResponse.size());
    }
}