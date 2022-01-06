package com.leverx.controllers;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.leverx.model.request.DepartmentRequest;
import com.leverx.model.response.DepartmentResponse;
import com.leverx.services.DepartmentService;
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
@RequestMapping("/departments")
public class DepartmentController {

  private final DepartmentService departmentService;
  private static final Logger log = LoggerFactory.logger(DepartmentController.class);

  @GetMapping
  @Operation(summary = "Get departments")
  public ResponseEntity<List<DepartmentResponse>> findAll() {
    List<DepartmentResponse> departments = departmentService.findAll();
    log.debug("Find departments");
    return new ResponseEntity<>(departments, OK);
  }

  @PostMapping
  @Operation(summary = "Save department")
  public ResponseEntity<DepartmentResponse> saveDepartment(@RequestBody DepartmentRequest request) {
    DepartmentResponse createdDepartment = departmentService.create(request);
    log.debug("Save department");
    return new ResponseEntity<>(createdDepartment, CREATED);
  }

  @PatchMapping("/{departmentId}")
  @Operation(summary = "Updated department")
  public ResponseEntity<DepartmentResponse> updateDepartment(
      @RequestBody DepartmentRequest request, @PathVariable Long departmentId) {
    DepartmentResponse updatedDepartment = departmentService.update(request, departmentId);
    log.debug("Update department");
    return new ResponseEntity<>(updatedDepartment, CREATED);
  }

  @DeleteMapping({"/{departmentId}"})
  @Operation(summary = "Delete department")
  public ResponseEntity deleteDepartment(@PathVariable Long departmentId) {
    departmentService.delete(departmentId);
    log.debug("Delete department");
    return new ResponseEntity<>(NO_CONTENT);
  }
}
