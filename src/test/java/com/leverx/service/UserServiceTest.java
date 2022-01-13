package com.leverx.service;

import com.leverx.model.Department;
import com.leverx.model.User;
import com.leverx.model.dto.request.UserRequestDto;
import com.leverx.model.dto.response.UserResponseDto;
import com.leverx.repository.DepartmentRepository;
import com.leverx.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

  @Mock UserRepository userRepository;

  @Mock DepartmentRepository departmentRepository;

  @InjectMocks UserService userService;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void whenFindAll_returnListUserResponse() {
    List<User> users = new ArrayList<>();
    Department department = new Department(1L, "title");
    User user1 = new User("firstName", "listName", "email", "password", "jobTitle", department);
    User user2 = new User("firstName", "listName", "email", "password", "jobTitle", department);
    User user3 = new User("firstName", "listName", "email", "password", "jobTitle", department);

    users.add(user1);
    users.add(user2);
    users.add(user3);

    when(userRepository.findAll()).thenReturn(users);

    List<UserResponseDto> userResponses = userService.findAll();
    int expectedSize = users.size();
    int actualSize = userResponses.size();

    Assertions.assertEquals(expectedSize, actualSize);
  }

  @Test
  void givenUserRequest_whenSaveUser_thenReturnUserResponse() {
    Department department = new Department(1L, "title");
    User user = new User(1L, "firstName", "lastName", "email", "password", "jobTitle", department);

    when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
    when(userRepository.save(any(User.class))).thenReturn(user);

    UserResponseDto userResponseDto =
        userService.create(
            new UserRequestDto("firstName", "lastName", "email", "password", "jobTitle", 1L));

    String actualFirstName = userResponseDto.getFirstName();
    String actualLastName = userResponseDto.getLastName();
    String actualJobTitle = userResponseDto.getPosition();
    Long actualDepartmentId = userResponseDto.getDepartmentId();

    String expectedFirstName = user.getFirstName();
    String expectedLastName = user.getLastName();
    String expectedJobTitle = user.getJobTitle();
    Long expectedDepartmentId = department.getId();

    Assertions.assertEquals(expectedFirstName, actualFirstName);
    Assertions.assertEquals(expectedLastName, actualLastName);
    Assertions.assertEquals(expectedJobTitle, actualJobTitle);
    Assertions.assertEquals(expectedDepartmentId, actualDepartmentId);
  }

  @Test
  void givenUserRequest_whenUpdateUser_thenReturnUserResponse() {
    Department department = new Department(1L, "title");
    User user = new User(1L, "firstName", "lastName", "email", "password", "jobTitle", department);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
    user.setFirstName("newName");
    when(userRepository.save(user)).thenReturn(user);

    UserResponseDto updateUserResponseDto =
        userService.update(
            new UserRequestDto("newName", "lastName", "email", "password", "jobTitle", 1L), 1L);
    String actualFirstName = updateUserResponseDto.getFirstName();
    String expectedFirstName = user.getFirstName();

    Assertions.assertEquals(expectedFirstName, actualFirstName);
  }

  @Test
  void givenUserRequest_whenDeleteUser() {
    Department department = new Department(1L, "title");
    User user = new User(1L, "firstName", "lastName", "email", "password", "jobTitle", department);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    doNothing().when(userRepository).deleteById(user.getId());

    userService.delete(1L);

    verify(userRepository, times(1)).deleteById(user.getId());
  }
}
