package com.leverx.controller;

import com.leverx.model.dto.request.ProjectPositionRequestDto;
import com.leverx.model.dto.response.ProjectPositionResponseDto;
import com.leverx.service.ProjectPositionService;
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
@RequestMapping("/positions")
public class ProjectPositionController {

  private final ProjectPositionService projectPositionService;
  private static final Logger log = LoggerFactory.logger(DepartmentController.class);

  @GetMapping
  @ResponseStatus(OK)
  @Operation(summary = "Get project positions")
  public List<ProjectPositionResponseDto> findAll() {
    List<ProjectPositionResponseDto> positions = projectPositionService.findAll();
    log.debug("Get project positions");
    return positions;
  }

  @PostMapping
  @ResponseStatus(CREATED)
  @Operation(summary = "Save project position")
  public ProjectPositionResponseDto saveProjectPosition(
          @RequestBody ProjectPositionRequestDto request) {
    ProjectPositionResponseDto createdPosition = projectPositionService.create(request);
    log.debug("Save project position");
    return createdPosition;
  }

  @PatchMapping("/{projectPositionId}")
  @ResponseStatus(OK)
  @Operation(summary = "Update project position")
  public ProjectPositionResponseDto updateProjectPosition(@RequestBody ProjectPositionRequestDto request, @PathVariable Long projectPositionId) {
    ProjectPositionResponseDto updatedPosition = projectPositionService.update(request, projectPositionId);
    log.debug("Update project position");
    return updatedPosition;
  }

  @DeleteMapping("/{projectPositionId}")
  @ResponseStatus(NO_CONTENT)
  @Operation(summary = "Delete project position")
  public void deleteProjectPosition(@PathVariable Long projectPositionId) {
    projectPositionService.delete(projectPositionId);
    log.debug("Delete project position");
  }
}
