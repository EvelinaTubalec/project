package com.leverx.controllers;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.leverx.model.request.ProjectRequest;
import com.leverx.model.response.ProjectResponse;
import com.leverx.services.ProjectService;
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
@RequestMapping("/projects")
public class ProjectController {

  private final ProjectService projectService;
  private static final Logger log = LoggerFactory.logger(DepartmentController.class);

  @GetMapping
  @Operation(summary = "Get projects")
  public ResponseEntity<List<ProjectResponse>> findAll() {
    List<ProjectResponse> projects = projectService.findAll();
    log.debug("Find projects");
    return new ResponseEntity<>(projects, OK);
  }

  @PostMapping
  @Operation(summary = "Save project")
  public ResponseEntity<ProjectResponse> saveProject(@RequestBody ProjectRequest request) {
    ProjectResponse createdProject = projectService.create(request);
    log.debug("Save project");
    return new ResponseEntity<>(createdProject, CREATED);
  }

  @PatchMapping("/{projectId}")
  @Operation(summary = "Update project")
  public ResponseEntity<ProjectResponse> updateProject(
      @RequestBody ProjectRequest request, @PathVariable Long projectId) {
    ProjectResponse updatedProject = projectService.update(request, projectId);
    log.debug("Update project");
    return new ResponseEntity<>(updatedProject, CREATED);
  }

  @DeleteMapping("/{projectId}")
  @Operation(summary = "Delete project")
  public ResponseEntity deleteProject(@PathVariable Long projectId) {
    projectService.delete(projectId);
    log.debug("Delete project");
    return new ResponseEntity<>(NO_CONTENT);
  }
}
