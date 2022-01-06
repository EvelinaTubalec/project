package com.leverx.controllers;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.leverx.model.request.UserRequest;
import com.leverx.model.response.UserResponse;
import com.leverx.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private static final Logger log = LoggerFactory.logger(DepartmentController.class);

  @GetMapping
  @Operation(summary = "Get users")
  public ResponseEntity<List<UserResponse>> findAll() {
    List<UserResponse> users = userService.findAll();
    log.debug("Find departments");
    return new ResponseEntity<>(users, OK);
  }

  @PostMapping
  @Operation(summary = "Save user")
  public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest request) {
    UserResponse createdUser = userService.create(request);
    log.debug("Save user");
    return new ResponseEntity<>(createdUser, CREATED);
  }

  @PatchMapping("/{userId}")
  @Operation(summary = "Update user")
  public ResponseEntity<UserResponse> updateUser(
      @RequestBody UserRequest request, @PathVariable Long userId) {
    UserResponse updatedUser = userService.update(request, userId);
    log.debug("Update user");
    return new ResponseEntity<>(updatedUser, CREATED);
  }

  @DeleteMapping("/{userId}")
  @Operation(summary = "Delete user")
  public ResponseEntity deleteUser(@PathVariable Long userId) {
    userService.delete(userId);
    log.debug("Delete user");
    return new ResponseEntity<>(NO_CONTENT);
  }
}
