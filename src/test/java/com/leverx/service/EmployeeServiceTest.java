package com.leverx.service;

import com.leverx.model.Department;
import com.leverx.model.Employee;
import com.leverx.model.convertor.AvailableUserConvertor;
import com.leverx.model.dto.request.DepartmentRequestDto;
import com.leverx.model.dto.request.EmployeeRequestDto;
import com.leverx.model.dto.response.AvailableEmployeeResponseDto;
import com.leverx.model.dto.response.EmployeeResponseDto;
import com.leverx.repository.DepartmentRepository;
import com.leverx.repository.EmployeeRepository;
import com.leverx.repository.ProjectPositionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class EmployeeServiceTest {

  @Mock
  EmployeeRepository employeeRepository;

  @Mock DepartmentRepository departmentRepository;

  @Mock
  ProjectPositionRepository projectPositionRepository;

  @InjectMocks
  EmployeeService employeeService;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void whenFindAll_returnListEmployeeResponse() {
    List<Employee> employees = new ArrayList<>();
    Department department = new Department(1L, "title");
    Employee employee1 = new Employee("firstName", "listName", "email", "password", "jobTitle", department);
    Employee employee2 = new Employee("firstName", "listName", "email", "password", "jobTitle", department);
    Employee employee3 = new Employee("firstName", "listName", "email", "password", "jobTitle", department);

    employees.add(employee1);
    employees.add(employee2);
    employees.add(employee3);

    when(employeeRepository.findAll()).thenReturn(employees);

    List<EmployeeResponseDto> userResponses = employeeService.findAll();
    int expectedSize = employees.size();
    int actualSize = userResponses.size();

    Assertions.assertEquals(expectedSize, actualSize);
  }

  @Test
  void givenEmployeeRequest_whenSaveEmployee_thenReturnEmployeeResponse() {
    Department department = new Department(1L, "title");
    Employee employee = new Employee(1L, "firstName", "lastName", "email", "password", "jobTitle", department);

    when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

    EmployeeResponseDto employeeResponseDto =
        employeeService.create(
            new EmployeeRequestDto("firstName", "lastName", "email", "password", "jobTitle", 1L));

    String actualFirstName = employeeResponseDto.getFirstName();
    String actualLastName = employeeResponseDto.getLastName();
    String actualJobTitle = employeeResponseDto.getJobTitle();
    Long actualDepartmentId = employeeResponseDto.getDepartmentId();

    String expectedFirstName = employee.getFirstName();
    String expectedLastName = employee.getLastName();
    String expectedJobTitle = employee.getJobTitle();
    Long expectedDepartmentId = department.getId();

    Assertions.assertEquals(expectedFirstName, actualFirstName);
    Assertions.assertEquals(expectedLastName, actualLastName);
    Assertions.assertEquals(expectedJobTitle, actualJobTitle);
    Assertions.assertEquals(expectedDepartmentId, actualDepartmentId);
  }

  @Test
  void givenEmployeeRequestWithoutFields_whenSaveEmployee_thenReturnResponseStatusException() {
    Throwable throwable =
            assertThrows(Throwable.class, () -> employeeService.create(new EmployeeRequestDto()));

    assertEquals(ResponseStatusException.class, throwable.getClass());
  }

  @Test
  void givenEmployeeRequest_whenUpdateEmployee_thenReturnEmployeeResponse() {
    Department department = new Department(1L, "title");
    Employee employee = new Employee(1L, "firstName", "lastName", "email", "password", "jobTitle", department);
    when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
    when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
    employee.setFirstName("newName");
    when(employeeRepository.save(employee)).thenReturn(employee);

    EmployeeResponseDto updateEmployeeResponseDto =
        employeeService.update(
            new EmployeeRequestDto("newName", "lastName", "email", "password", "jobTitle", 1L), 1L);
    String actualFirstName = updateEmployeeResponseDto.getFirstName();
    String expectedFirstName = employee.getFirstName();

    Assertions.assertEquals(expectedFirstName, actualFirstName);
  }

  @Test
  void givenEmployeeRequest_whenDeleteEmployee() {
    Department department = new Department(1L, "title");
    Employee employee = new Employee(1L, "firstName", "lastName", "email", "password", "jobTitle", department);
    when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
    doNothing().when(employeeRepository).deleteById(employee.getId());

    employeeService.delete(1L);

    verify(employeeRepository, times(1)).deleteById(employee.getId());
  }

  @Test
  void whenFindAllAvailableEmployees_returnListAvailableEmployeesResponse() {
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
    for (Employee e : listOfAvailableEmployees) {
      LocalDate availableFrom = LocalDate.of(2022, 1, 1);
      LocalDate availableTo = LocalDate.of(2022, 2, 25);
      when(projectPositionRepository.findAvailableDateOfEmployee(e.getId(), date)).thenReturn(availableTo);
      when(projectPositionRepository.findLastProjectPositionDateOfEmployee(e.getId(), date)).thenReturn(availableFrom);

      AvailableEmployeeResponseDto availableEmployeeResponseDto = AvailableUserConvertor.toResponse(e, availableTo, availableFrom);
      expectedResponse.add(availableEmployeeResponseDto);
    }

    List<AvailableEmployeeResponseDto> actualResponse = employeeService.findListOfAvailableEmployees(LocalDate.now());
    Assertions.assertEquals(expectedResponse.size(), actualResponse.size());
  }
}
