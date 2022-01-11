package com.leverx.model.convertor;

import com.leverx.model.Department;
import com.leverx.model.User;
import com.leverx.model.dto.request.UserRequestDto;
import com.leverx.model.dto.response.UserResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserConvertor {

  public UserConvertor() {
  }

  public static UserResponseDto toResponse(User user) {
    return UserResponseDto.builder()
        .userId(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .position(user.getJobTitle())
        .departmentId(user.getDepartment().getId())
        .build();
  }

  public static User toEntity(UserRequestDto request, Department department) {
    return User.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .password(request.getPassword())
        .jobTitle(request.getPosition())
        .department(department)
        .build();
  }

  public static User toEntity(UserRequestDto request, User user, Department department) {
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setPassword(request.getPassword());
    user.setJobTitle(request.getPosition());
    user.setDepartment(department);
    return user;
  }

  public static List<UserResponseDto> convertToListUserResponse(List<User> users) {
    return users.stream()
        .map(
            user ->
                UserResponseDto.builder()
                    .userId(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .position(user.getJobTitle())
                    .departmentId(user.getDepartment().getId())
                    .build())
        .collect(Collectors.toList());
  }
}
