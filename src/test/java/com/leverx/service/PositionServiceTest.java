package com.leverx.service;

import com.leverx.model.Department;
import com.leverx.model.Position;
import com.leverx.model.Project;
import com.leverx.model.User;
import com.leverx.model.dto.request.PositionRequestDto;
import com.leverx.model.dto.response.PositionResponseDto;
import com.leverx.repository.PositionRepository;
import com.leverx.repository.ProjectRepository;
import com.leverx.repository.UserRepository;
import lombok.AllArgsConstructor;
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
class PositionServiceTest {

  @Mock PositionRepository positionRepository;

  @Mock UserRepository userRepository;

  @Mock ProjectRepository projectRepository;

  @InjectMocks PositionService positionService;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void whenfindAll_thenReturnListPositionResponse() {
    List<Position> positions = new ArrayList<>();
    Department department = new Department(1L, "title");
    User user = new User(1L, "firstName", "lastName", "email", "password", "jobTitle", department);
    Project project = new Project(1L, "title", LocalDate.now(), LocalDate.now());

    Position position1 = new Position(1L, LocalDate.now(), LocalDate.now(), project, user);
    Position position2 = new Position(2L, LocalDate.now(), LocalDate.now(), project, user);
    Position position3 = new Position(3L, LocalDate.now(), LocalDate.now(), project, user);

    positions.add(position1);
    positions.add(position2);
    positions.add(position3);

    when(positionRepository.findAll()).thenReturn(positions);

    List<PositionResponseDto> positionResponses = positionService.findAll();
    int expectedSize = positions.size();
    int actualSize = positionResponses.size();

    Assertions.assertEquals(expectedSize, actualSize);
  }

  @Test
  void givenPositionRequest_whenSavePosition_thenReturnPositionResponse() {
    Department department = new Department(1L, "title");
    User user = new User(1L, "firstName", "lastName", "email", "password", "jobTitle", department);
    Project project = new Project(1L, "title", LocalDate.now(), LocalDate.now());
    Position positionDb = new Position(1L, LocalDate.now(), LocalDate.now(), project, user);
    when(positionRepository.save(any(Position.class))).thenReturn(positionDb);

    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

    PositionResponseDto positionResponseDto = positionService.create(new PositionRequestDto(1L, 1L, LocalDate.now(), LocalDate.now()));

    LocalDate actualPositionStartDate = positionResponseDto.getPositionStartDate();
    LocalDate actualPositionEndDate = positionResponseDto.getPositionEndDate();

    LocalDate expectedPositionStartDate = positionDb.getPositionStartDate();
    LocalDate expectedPositionEndDate = positionDb.getPositionEndDate();

    Assertions.assertEquals(expectedPositionStartDate, actualPositionStartDate);
    Assertions.assertEquals(expectedPositionEndDate, actualPositionEndDate);
  }

  @Test
  void givenPositionRequest_whenUpdatePosition_thenReturnPositionResponse() {
    Department department = new Department(1L, "title");
    User user = new User(1L, "firstName", "lastName", "email", "password", "jobTitle", department);
    Project project = new Project(1L, "title", LocalDate.now(), LocalDate.now());
    Position positionDb = new Position(1L, LocalDate.now(), LocalDate.now(), project, user);
    when(positionRepository.findById(1L)).thenReturn(Optional.of(positionDb));
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
    positionDb.setPositionEndDate(LocalDate.of(2022, 12, 12));
    when(positionRepository.save(positionDb)).thenReturn(positionDb);

    PositionResponseDto updatePositionResponseDto = positionService.update(new PositionRequestDto(1L, 1L, LocalDate.now(), LocalDate.of(2022, 12, 12)), 1L);
    LocalDate actualPositionEndDate = updatePositionResponseDto.getPositionEndDate();
    LocalDate expectedPositionEndDate = positionDb.getPositionEndDate();

    Assertions.assertEquals(expectedPositionEndDate, actualPositionEndDate);
  }

  @Test
  void givenPositionRequest_whenDeletePosition() {
    Department department = new Department(1L, "title");
    User user = new User(1L, "firstName", "lastName", "email", "password", "jobTitle", department);
    Project project = new Project(1L, "title", LocalDate.now(), LocalDate.now());
    Position positionDb = new Position(1L, LocalDate.now(), LocalDate.now(), project, user);
    when(positionRepository.findById(1L)).thenReturn(Optional.of(positionDb));
    doNothing().when(positionRepository).deleteById(positionDb.getId());

    positionService.delete(1L);

    verify(positionRepository, times(1)).deleteById(positionDb.getId());
  }
}
