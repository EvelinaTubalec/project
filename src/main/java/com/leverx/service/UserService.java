package com.leverx.service;

import com.leverx.model.Department;
import com.leverx.model.User;
import com.leverx.model.dto.request.UserRequestDto;
import com.leverx.model.dto.response.UserResponseDto;
import com.leverx.model.convertor.UserConvertor;
import com.leverx.repository.DepartmentRepository;
import com.leverx.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final DepartmentRepository departmentRepository;

  public List<UserResponseDto> findAll() {
    List<User> users = userRepository.findAll();
    return UserConvertor.convertToListUserResponse(users);
  }

  public UserResponseDto create(UserRequestDto request) {
    Department departmentById = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Department with id = " + request.getDepartmentId() + " doesn't exists"));
    User user = UserConvertor.toEntity(request, departmentById);
    User savedUser = userRepository.save(user);
    return UserConvertor.toResponse(savedUser);
  }

  public UserResponseDto update(UserRequestDto request, Long userId) {
    User userById = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "User with id = " + userId + " doesn't exists"));
    Department departmentById = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Department with id = " + request.getDepartmentId() + " doesn't exists"));
    User user = UserConvertor.toEntity(request, userById, departmentById);
    User updatedUser = userRepository.save(user);
    return UserConvertor.toResponse(updatedUser);
  }

  public void delete(Long userId) {
    userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "User with id = " + userId + " doesn't exists"));
    userRepository.deleteById(userId);
  }
}
