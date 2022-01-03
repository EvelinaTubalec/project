package com.leverx.controllers;

import com.leverx.model.request.UserRequest;
import com.leverx.model.response.UserResponse;
import com.leverx.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserResponse> saveUser(UserRequest request){
        return new ResponseEntity<>(userService.create(request), HttpStatus.CREATED);
    }
    

}
