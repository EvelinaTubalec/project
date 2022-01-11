package com.leverx.controller;

import com.leverx.model.dto.request.UserRequestDto;
import com.leverx.model.dto.response.UserResponseDto;
import com.leverx.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private static final Logger log = LoggerFactory.logger(DepartmentController.class);

  @GetMapping
  @ResponseStatus(OK)
  @Operation(summary = "Get users")
  public List<UserResponseDto> findAll() {
    List<UserResponseDto> users = userService.findAll();
    log.debug("Find departments");
    return users;
  }

  @PostMapping
  @ResponseStatus(CREATED)
  @Operation(summary = "Save user")
  public UserResponseDto saveUser(@RequestBody UserRequestDto request) {
    UserResponseDto createdUser = userService.create(request);
    log.debug("Save user");
    return createdUser;
  }

  @PatchMapping("/{userId}")
  @ResponseStatus(OK)
  @Operation(summary = "Update user")
  public UserResponseDto updateUser(
          @RequestBody UserRequestDto request, @PathVariable Long userId) {
    UserResponseDto updatedUser = userService.update(request, userId);
    log.debug("Update user");
    return updatedUser;
  }

  @DeleteMapping("/{userId}")
  @ResponseStatus(NO_CONTENT)
  @Operation(summary = "Delete user")
  public void deleteUser(@PathVariable Long userId) {
    userService.delete(userId);
    log.debug("Delete user");
  }
}
