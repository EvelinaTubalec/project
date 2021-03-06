package com.leverx.service;

import com.leverx.model.Project;
import com.leverx.model.dto.request.ProjectRequestDto;
import com.leverx.model.dto.response.ProjectResponseDto;
import com.leverx.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ProjectServiceTest {

  @Mock ProjectRepository projectRepository;

  @InjectMocks ProjectService projectService;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void whenFindAll_thenReturnListProjectResponse() {
    List<Project> actualProjects = new ArrayList<>();
    Project project1 = new Project(1L, "title", LocalDate.now(), LocalDate.now());
    Project project2 = new Project(1L, "title", LocalDate.now(), LocalDate.now());
    Project project3 = new Project(1L, "title", LocalDate.now(), LocalDate.now());

    actualProjects.add(project1);
    actualProjects.add(project2);
    actualProjects.add(project3);

    when(projectRepository.findAll()).thenReturn(actualProjects);

    List<ProjectResponseDto> projectResponses = projectService.findAll();

    int actual = projectResponses.size();
    int expected = actualProjects.size();

    Assertions.assertEquals(expected, actual);
  }

  @Test
  void givenProjectRequest_whenSaveProject_thenReturnProjectResponse() {
    Project project = new Project(1L, "title", LocalDate.now(), LocalDate.now());
    when(projectRepository.save(any(Project.class))).thenReturn(project);

    ProjectResponseDto projectResponseDto =
        projectService.create(new ProjectRequestDto("title", LocalDate.now(), LocalDate.now()));
    String expectedTitle = project.getTitle();
    LocalDate expectedStartDate = project.getStartDate();
    LocalDate expectedEndDate = project.getEndDate();

    String actualTitle = projectResponseDto.getTitle();
    LocalDate actualStartDate = projectResponseDto.getStartDate();
    LocalDate actualEndDate = projectResponseDto.getEndDate();

    Assertions.assertEquals(expectedTitle, actualTitle);
    Assertions.assertEquals(expectedStartDate, actualStartDate);
    Assertions.assertEquals(expectedEndDate, actualEndDate);
  }

  @Test
  void givenProjectRequest_whenUpdateProject_thenReturnProjectResponse() {
    Project project = new Project(1L, "title", LocalDate.now(), LocalDate.now());
    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
    project.setTitle("new");
    when(projectRepository.save(project)).thenReturn(project);

    ProjectResponseDto projectResponseDto =
        projectService.update(new ProjectRequestDto("title", LocalDate.now(), LocalDate.now()), 1L);

    String expectedTitle = project.getTitle();
    LocalDate expectedStartDate = project.getStartDate();
    LocalDate expectedEndDate = project.getEndDate();

    String actualTitle = projectResponseDto.getTitle();
    LocalDate actualStartDate = projectResponseDto.getStartDate();
    LocalDate actualEndDate = projectResponseDto.getEndDate();

    Assertions.assertEquals(expectedTitle, actualTitle);
    Assertions.assertEquals(expectedStartDate, actualStartDate);
    Assertions.assertEquals(expectedEndDate, actualEndDate);
  }

  @Test
  void givenProjectRequest_whenDeleteProject() {
    Project project = new Project(1L, "title", LocalDate.now(), LocalDate.now());
    when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
    doNothing().when(projectRepository).deleteById(project.getId());

    projectService.delete(1L);

    verify(projectRepository, times(1)).deleteById(project.getId());
  }
}
