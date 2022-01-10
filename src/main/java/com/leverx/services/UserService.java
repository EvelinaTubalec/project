package com.leverx.services;

import com.leverx.model.User;
import com.leverx.model.dto.requests.UserRequest;
import com.leverx.model.dto.responses.UserResponse;
import com.leverx.model.utils.convertors.UserConvertor;
import com.leverx.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserConvertor userConvertor;

  public List<UserResponse> findAll() {
    List<User> users = userRepository.findAll();
    return userConvertor.convertToListUserResponse(users);
  }

  public UserResponse create(UserRequest request) {
    User user = userConvertor.convertRequestToUser(request);
    User savedUser = userRepository.save(user);
    return userConvertor.convertUserToResponse(savedUser);
  }

  public UserResponse update(UserRequest request, Long userId) {
    User user = userConvertor.convertRequestToUser(request, userId);
    User updatedUser = userRepository.save(user);
    return userConvertor.convertUserToResponse(updatedUser);
  }

  public void delete(Long userId) {
    userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "User with id = " + userId + " doesn't exists"));
    userRepository.deleteById(userId);
  }
}
