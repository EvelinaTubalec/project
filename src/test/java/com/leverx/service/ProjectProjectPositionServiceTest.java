package com.leverx.service;

import com.leverx.model.Department;
import com.leverx.model.Employee;
import com.leverx.model.Project;
import com.leverx.model.ProjectPosition;
import com.leverx.model.dto.request.ProjectPositionRequestDto;
import com.leverx.model.dto.response.ProjectPositionResponseDto;
import com.leverx.repository.EmployeeRepository;
import com.leverx.repository.ProjectPositionRepository;
import com.leverx.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
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
class ProjectProjectPositionServiceTest {

  @Mock
  ProjectPositionRepository projectPositionRepository;

  @Mock
  EmployeeRepository employeeRepository;

  @Mock ProjectRepository projectRepository;

  @InjectMocks
  ProjectPositionService projectPositionService;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void whenfindAll_thenReturnListPositionResponse() {
    List<ProjectPosition> projectPositions = new ArrayList<>();
    Department department = new Department(1L, "title");
    Employee employee = new Employee(1L, "firstName", "lastName", "email", "password", "jobTitle", department);
    Project project = new Project(1L, "title", LocalDate.now(), LocalDate.now());

    ProjectPosition projectPosition1 = new ProjectPosition(1L, LocalDate.now(), LocalDate.now(), project, employee);
    ProjectPosition projectPosition2 = new ProjectPosition(2L, LocalDate.now(), LocalDate.now(), project, employee);
    ProjectPosition projectPosition3 = new ProjectPosition(3L, LocalDate.now(), LocalDate.now(), project, employee);

    projectPositions.add(projectPosition1);
    projectPositions.add(projectPosition2);
    projectPositions.add(projectPosition3);

    when(projectPositionRepository.findAll()).thenReturn(projectPositions);

    List<ProjectPositionResponseDto> positionResponses = projectPositionService.findAll();
    int expectedSize = projectPositions.size();
    int actualSize = positionResponses.size();

    Assertions.assertEquals(expectedSize, actualSize);
  }

  @Test
  void givenPositionRequest_whenSavePosition_thenReturnPositionResponse() {
    Department department = new Department(1L, "title");
    Employee employee = new Employee(1L, "firstName", "lastName", "email", "password", "jobTitle", department);
    Project project = new Project(1L, "title", LocalDate.now(), LocalDate.now());
    ProjectPosition projectPositionDb = new ProjectPosition(1L, LocalDate.now(), LocalDate.now(), project, employee);
    when(projectPositionRepository.save(any(ProjectPosition.class))).thenReturn(projectPositionDb);

    when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

    ProjectPositionResponseDto projectPositionResponseDto =
        projectPositionService.create(new ProjectPositionRequestDto(1L, 1L, LocalDate.now(), LocalDate.now()));

    LocalDate actualPositionStartDate = projectPositionResponseDto.getPositionStartDate();
    LocalDate actualPositionEndDate = projectPositionResponseDto.getPositionEndDate();

    LocalDate expectedPositionStartDate = projectPositionDb.getPositionStartDate();
    LocalDate expectedPositionEndDate = projectPositionDb.getPositionEndDate();

    Assertions.assertEquals(expectedPositionStartDate, actualPositionStartDate);
    Assertions.assertEquals(expectedPositionEndDate, actualPositionEndDate);
  }

  @Test
  void givenProjectPositionRequest_whenUpdateProjectPositionNonExistingProjectPosition_thenReturnResponseStatusException() {
    Department department = new Department(1L, "title");
    Employee employee = new Employee(1L, "firstName", "lastName", "email", "password", "jobTitle", department);
    Project project = new Project(100L, "title", LocalDate.now(), LocalDate.now());
    ProjectPosition projectPosition = new ProjectPosition(1L, LocalDate.now(), LocalDate.now(), project, employee);

    when(projectPositionRepository.findById(projectPosition.getId()))
        .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project position with " + projectPosition.getId() + " doesn't exists"));

    Throwable throwable =
            assertThrows(Throwable.class, () -> projectPositionService.update(new ProjectPositionRequestDto(1L, 1L, LocalDate.now(), LocalDate.now()), 100L));

    assertEquals(ResponseStatusException.class, throwable.getClass());
  }

  @Test
  void givenPositionRequestWithoutFields_whenSavePositionRequest_thenReturnResponseStatusException() {
    Throwable throwable =
            assertThrows(Throwable.class, () -> projectPositionService.create(new ProjectPositionRequestDto()));

    assertEquals(ResponseStatusException.class, throwable.getClass());
  }

  @Test
  void givenPositionRequest_whenUpdatePosition_thenReturnPositionResponse() {
    Department department = new Department(1L, "title");
    Employee employee = new Employee(1L, "firstName", "lastName", "email", "password", "jobTitle", department);
    Project project = new Project(1L, "title", LocalDate.now(), LocalDate.now());
    ProjectPosition projectPositionDb = new ProjectPosition(1L, LocalDate.now(), LocalDate.now(), project, employee);
    when(projectPositionRepository.findById(1L)).thenReturn(Optional.of(projectPositionDb));
    when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
    projectPositionDb.setPositionEndDate(LocalDate.of(2022, 12, 12));
    when(projectPositionRepository.save(projectPositionDb)).thenReturn(projectPositionDb);

    ProjectPositionResponseDto updateProjectPositionResponseDto =
        projectPositionService.update(
            new ProjectPositionRequestDto(1L, 1L, LocalDate.now(), LocalDate.of(2022, 12, 12)), 1L);
    LocalDate actualPositionEndDate = updateProjectPositionResponseDto.getPositionEndDate();
    LocalDate expectedPositionEndDate = projectPositionDb.getPositionEndDate();

    Assertions.assertEquals(expectedPositionEndDate, actualPositionEndDate);
  }

  @Test
  void givenPositionRequest_whenDeletePosition() {
    Department department = new Department(1L, "title");
    Employee employee = new Employee(1L, "firstName", "lastName", "email", "password", "jobTitle", department);
    Project project = new Project(1L, "title", LocalDate.now(), LocalDate.now());
    ProjectPosition projectPositionDb = new ProjectPosition(1L, LocalDate.now(), LocalDate.now(), project, employee);
    when(projectPositionRepository.findById(1L)).thenReturn(Optional.of(projectPositionDb));
    doNothing().when(projectPositionRepository).deleteById(projectPositionDb.getId());

    projectPositionService.delete(1L);

    verify(projectPositionRepository, times(1)).deleteById(projectPositionDb.getId());
  }
}
