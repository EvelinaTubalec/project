package com.leverx.controllers;

import com.leverx.model.dto.ProjectRequest;
import com.leverx.model.response.ProjectResponse;
import com.leverx.services.ProjectService;
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
@RequestMapping("/projects")
public class ProjectController {

  private final ProjectService projectService;
  private static final Logger log = LoggerFactory.logger(DepartmentController.class);

  @GetMapping
  @ResponseStatus(OK)
  @Operation(summary = "Get projects")
  public List<ProjectResponse> findAll() {
    List<ProjectResponse> projects = projectService.findAll();
    log.debug("Find projects");
    return projects;
  }

  @PostMapping
  @ResponseStatus(CREATED)
  @Operation(summary = "Save project")
  public ProjectResponse saveProject(@RequestBody ProjectRequest request) {
    ProjectResponse createdProject = projectService.create(request);
    log.debug("Save project");
    return createdProject;
  }

  @PatchMapping("/{projectId}")
  @ResponseStatus(CREATED)
  @Operation(summary = "Update project")
  public ProjectResponse updateProject(@RequestBody ProjectRequest request, @PathVariable Long projectId) {
    ProjectResponse updatedProject = projectService.update(request, projectId);
    log.debug("Update project");
    return updatedProject;
  }

  @DeleteMapping("/{projectId}")
  @ResponseStatus(NO_CONTENT)
  @Operation(summary = "Delete project")
  public void deleteProject(@PathVariable Long projectId) {
    projectService.delete(projectId);
    log.debug("Delete project");
  }
}
