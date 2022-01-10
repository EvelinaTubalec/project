package com.leverx.controllers;

import com.leverx.model.dto.PositionRequest;
import com.leverx.model.response.PositionResponse;
import com.leverx.services.PositionService;
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
public class PositionController {

  private final PositionService positionService;
  private static final Logger log = LoggerFactory.logger(DepartmentController.class);

  @GetMapping
  @ResponseStatus(OK)
  @Operation(summary = "Get userProjects")
  public List<PositionResponse> findAll() {
    List<PositionResponse> userProjects = positionService.findAll();
    log.debug("Get userProjects");
    return userProjects;
  }

  @PostMapping
  @ResponseStatus(CREATED)
  @Operation(summary = "Save userProject")
  public PositionResponse saveUserProject(
          @RequestBody PositionRequest request) {
    PositionResponse createdUserProject = positionService.create(request);
    log.debug("Save userProject");
    return createdUserProject;
  }

  @PatchMapping("/{userProjectId}")
  @ResponseStatus(CREATED)
  @Operation(summary = "Update userProject")
  public PositionResponse updateUserProject(@RequestBody PositionRequest request, @PathVariable Long userProjectId) {
    PositionResponse updatedUserProject = positionService.update(request, userProjectId);
    log.debug("Update userProject");
    return updatedUserProject;
  }

  @DeleteMapping("/{userProjectId}")
  @ResponseStatus(NO_CONTENT)
  @Operation(summary = "Delete userProject")
  public void deleteUserProject(@PathVariable Long userProjectId) {
    positionService.delete(userProjectId);
    log.debug("Delete userProject");
  }
}
