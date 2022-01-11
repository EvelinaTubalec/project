package com.leverx.controllers;

import com.leverx.model.dto.requests.PositionRequest;
import com.leverx.model.dto.responses.PositionResponse;
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
  @Operation(summary = "Get positions")
  public List<PositionResponse> findAll() {
    List<PositionResponse> positions = positionService.findAll();
    log.debug("Get userProjects");
    return positions;
  }

  @PostMapping
  @ResponseStatus(CREATED)
  @Operation(summary = "Save position")
  public PositionResponse savePosition(
          @RequestBody PositionRequest request) {
    PositionResponse createdPosition = positionService.create(request);
    log.debug("Save userProject");
    return createdPosition;
  }

  @PatchMapping("/{positionId}")
  @ResponseStatus(OK)
  @Operation(summary = "Update position")
  public PositionResponse updatePosition(@RequestBody PositionRequest request, @PathVariable Long positionId) {
    PositionResponse updatedPosition = positionService.update(request, positionId);
    log.debug("Update userProject");
    return updatedPosition;
  }

  @DeleteMapping("/{positionId}")
  @ResponseStatus(NO_CONTENT)
  @Operation(summary = "Delete position")
  public void deletePosition(@PathVariable Long positionId) {
    positionService.delete(positionId);
    log.debug("Delete userProject");
  }
}
