package com.leverx.controllers;

import com.leverx.model.request.UserProjectRequest;
import com.leverx.model.response.UserProjectResponse;
import com.leverx.services.UserProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Controller
@AllArgsConstructor
@RequestMapping("/user_projects")
public class UserProjectController {

    private final UserProjectService userProjectService;

    @GetMapping()
    public ResponseEntity<List<UserProjectResponse>> findAll(){
        List<UserProjectResponse> userProjects = userProjectService.findAll();
        return new ResponseEntity<>(userProjects, OK);
    }

    @PostMapping()
    public ResponseEntity<UserProjectResponse> saveUserProject(@RequestBody UserProjectRequest request){
        UserProjectResponse createdUserProject = userProjectService.create(request);
        return new ResponseEntity<>(createdUserProject, CREATED);
    }

    @PatchMapping("{userProjectId}")
    public ResponseEntity<UserProjectResponse> updateUserProject(@RequestBody UserProjectRequest request, @PathVariable Long userProjectId){
        UserProjectResponse updatedUserProject = userProjectService.update(request, userProjectId);
        return new ResponseEntity<>(updatedUserProject, CREATED);
    }

    @DeleteMapping("{userProjectId}")
    public ResponseEntity deleteUserProject(@PathVariable Long userProjectId){
        userProjectService.delete(userProjectId);
        return new ResponseEntity<>(OK);
    }
}
