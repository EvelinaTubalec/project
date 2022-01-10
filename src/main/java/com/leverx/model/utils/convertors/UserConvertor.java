package com.leverx.model.utils.convertors;

import com.leverx.model.User;
import com.leverx.model.dto.requests.UserRequest;
import com.leverx.model.dto.responses.UserResponse;
import com.leverx.repositories.DepartmentRepository;
import com.leverx.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserConvertor {

  private final DepartmentRepository departmentRepository;
  private final UserRepository userRepository;

  public UserResponse convertUserToResponse(User user) {
    return UserResponse.builder()
        .userId(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .position(user.getPosition())
        .departmentId(user.getDepartment().getId())
        .build();
  }

  public User convertRequestToUser(UserRequest request) {
    return User.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .password(request.getPassword())
        .position(request.getPosition())
        .department(
            departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(
                    () ->
                        new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Department with id = "
                                + request.getDepartmentId()
                                + " doesn't exists")))
        .build();
  }

  public User convertRequestToUser(UserRequest request, Long userId) {
    User userById = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "User with id = " + userId + " doesn't exists"));
    userById.setFirstName(request.getFirstName());
    userById.setLastName(request.getLastName());
    userById.setEmail(request.getEmail());
    userById.setPassword(request.getPassword());
    userById.setPosition(request.getPosition());
    userById.setDepartment(departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Game with id = " + request.getDepartmentId() + " doesn't exists")));
    return userById;
  }

  public List<UserResponse> convertToListUserResponse(List<User> users) {
    return users.stream()
        .map(
            user ->
                UserResponse.builder()
                    .userId(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .position(user.getPosition())
                    .departmentId(user.getDepartment().getId())
                    .build())
        .collect(Collectors.toList());
  }
}
