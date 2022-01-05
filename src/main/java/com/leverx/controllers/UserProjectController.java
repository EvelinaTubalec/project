package com.leverx.controllers;

import com.leverx.model.request.UserProjectRequest;
import com.leverx.model.response.UserProjectResponse;
import com.leverx.services.UserProjectService;
import io.swagger.annotations.ApiOperation;
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

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/user_projects")
public class UserProjectController {

    private final UserProjectService userProjectService;
    private static final Logger log = LoggerFactory.logger(DepartmentController.class);

    @GetMapping
    @ApiOperation(value = "Get userProjects")
    public ResponseEntity<List<UserProjectResponse>> findAll(){
        List<UserProjectResponse> userProjects = userProjectService.findAll();
        log.debug("Get userProjects");
        return new ResponseEntity<>(userProjects, OK);
    }

    @PostMapping
    @ApiOperation(value = "Save userProject")
    public ResponseEntity<UserProjectResponse> saveUserProject(@RequestBody UserProjectRequest request){
        UserProjectResponse createdUserProject = userProjectService.create(request);
        log.debug("Save userProject");
        return new ResponseEntity<>(createdUserProject, CREATED);
    }

    @PatchMapping("/{userProjectId}")
    @ApiOperation(value = "Update userProject")
    public ResponseEntity<UserProjectResponse> updateUserProject(@RequestBody UserProjectRequest request, @PathVariable Long userProjectId){
        UserProjectResponse updatedUserProject = userProjectService.update(request, userProjectId);
        log.debug("Update userProject");
        return new ResponseEntity<>(updatedUserProject, CREATED);
    }

    @DeleteMapping("/{userProjectId}")
    @ApiOperation(value = "Delete userProject")
    public ResponseEntity deleteUserProject(@PathVariable Long userProjectId){
        userProjectService.delete(userProjectId);
        log.debug("Delete userProject");
        return new ResponseEntity<>(NO_CONTENT);
    }
}
