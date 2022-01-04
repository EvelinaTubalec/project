package com.leverx.controllers;

import com.leverx.model.request.ProjectRequest;
import com.leverx.model.response.ProjectResponse;
import com.leverx.services.ProjectService;
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
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping()
    public ResponseEntity<List<ProjectResponse>> findAll(){
        List<ProjectResponse> projects = projectService.findAll();
        return new ResponseEntity<>(projects, OK);
    }

    @PostMapping()
    public ResponseEntity<ProjectResponse> saveProject(@RequestBody ProjectRequest request){
        ProjectResponse createdProject = projectService.create(request);
        return new ResponseEntity<>(createdProject, CREATED);
    }

    @PatchMapping("{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(@RequestBody ProjectRequest request, @PathVariable Long projectId){
        ProjectResponse updatedProject = projectService.update(request, projectId);
        return new ResponseEntity<>(updatedProject, CREATED);
    }

    @DeleteMapping("{projectId}")
    public ResponseEntity deleteProject(@PathVariable Long projectId){
        projectService.delete(projectId);
        return new ResponseEntity<>(OK);
    }
}
