package com.leverx.service;

import com.leverx.model.Department;
import com.leverx.model.dto.request.DepartmentRequestDto;
import com.leverx.model.dto.response.DepartmentResponseDto;
import com.leverx.repository.DepartmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

class DepartmentServiceTest {

  @Mock DepartmentRepository departmentRepository;

  @InjectMocks DepartmentService departmentService;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void whenFindAll_thenReturnListDepartmentResponse() {
    List<Department> departments = new ArrayList<>();

    Department department1 = new Department(1L, "title");
    Department department2 = new Department(2L, "title");
    Department department3 = new Department(3L, "title");

    departments.add(department1);
    departments.add(department2);
    departments.add(department3);

    when(departmentRepository.findAll()).thenReturn(departments);

    List<DepartmentResponseDto> departmentResponses = departmentService.findAll();
    int actual = departmentResponses.size();
    int expected = departments.size();

    Assertions.assertEquals(expected, actual);
    departmentResponses.stream()
        .map(DepartmentResponseDto::getTitle)
        .forEach(
            actualTitle -> {
              String expectedTitle = "title";
              assertEquals(expectedTitle, actualTitle);
            });
  }

  @Test
  void givenDepartmentRequest_whenSaveDepartment_thenReturnDepartmentResponse() {
    Department savedDepartmentDb = new Department(1L, "title");
    when(departmentRepository.save(any(Department.class))).thenReturn(savedDepartmentDb);

    DepartmentResponseDto departmentDtoResponse =
        departmentService.create(new DepartmentRequestDto("title"));

    String expectedTitle = savedDepartmentDb.getTitle();
    String actualTitle = departmentDtoResponse.getTitle();

    Long expectedId = savedDepartmentDb.getId();
    Long actualId = departmentDtoResponse.getDepartmentId();

    Assertions.assertEquals(expectedTitle, actualTitle);
    Assertions.assertEquals(expectedId, actualId);
  }

  @Test
  void givenDepartmentRequestWithoutTitle_whenSaveDepartment_thenReturnNullPointerException() {
    Throwable throwable =
        assertThrows(Throwable.class, () -> departmentService.create(new DepartmentRequestDto()));

    assertEquals(ResponseStatusException.class, throwable.getClass());
  }

  @Test
  void givenDepartmentRequest_whenUpdateDepartment_thenReturnDepartmentResponse() {
    Department department = new Department(1L, "title");
    when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
    department.setTitle("new");
    when(departmentRepository.save(department)).thenReturn(department);

    DepartmentResponseDto actualDepartmentResponseDto =
        departmentService.update(new DepartmentRequestDto("new"), 1L);

    String actualTitle = actualDepartmentResponseDto.getTitle();
    String expected = department.getTitle();
    Assertions.assertEquals(expected, actualTitle);
  }

  @Test
  void
      givenDepartmentRequest_whenUpdateDepartmentNonExistingDepartment_thenReturnResponseStatusException() {
    Department department = new Department(1L, "title");
    when(departmentRepository.findById(department.getId()))
        .thenThrow(
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Department with" + department.getId() + " doesn't exists"));

    Throwable throwable =
        assertThrows(
            Throwable.class, () -> departmentService.update(new DepartmentRequestDto("new"), 1L));

    assertEquals(ResponseStatusException.class, throwable.getClass());
  }

  @Test
  void givenDepartmentRequest_whenDeleteDepartment() {
    Department department = new Department(1L, "title");
    when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
    doNothing().when(departmentRepository).deleteById(department.getId());

    departmentService.delete(1L);

    verify(departmentRepository, times(1)).deleteById(department.getId());
  }

  @Test
  void
      givenDepartmentRequest_whenDeleteDepartmentInsistingDepartment_thenReturnResponseStatusException() {
    Department department = new Department(1L, "title");
    when(departmentRepository.findById(department.getId()))
        .thenThrow(
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Department with" + department.getId() + " doesn't exists"));

    Throwable throwable = assertThrows(Throwable.class, () -> departmentService.delete(1L));

    assertEquals(ResponseStatusException.class, throwable.getClass());
  }
}
