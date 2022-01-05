package com.leverx.controllers;

import com.leverx.model.request.UserRequest;
import com.leverx.model.response.UserResponse;
import com.leverx.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserResponse>> findAll(){
        List<UserResponse> users = userService.findAll();
        return new ResponseEntity<>(users, OK);
    }

    @PostMapping()
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest request){
        UserResponse createdUser = userService.create(request);
        return new ResponseEntity<>(createdUser, CREATED);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest request, @PathVariable Long userId){
        UserResponse updatedUser = userService.update(request, userId);
        return new ResponseEntity<>(updatedUser, CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        userService.delete(userId);
        return new ResponseEntity<>(OK);
    }
}
