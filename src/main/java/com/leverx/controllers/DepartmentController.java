package com.leverx.controllers;

import com.leverx.model.dto.requests.DepartmentRequest;
import com.leverx.model.dto.responses.DepartmentResponse;
import com.leverx.services.DepartmentService;
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
@RequestMapping("/departments")
public class DepartmentController {

  private final DepartmentService departmentService;
  private static final Logger log = LoggerFactory.logger(DepartmentController.class);

  @GetMapping
  @ResponseStatus(OK)
  @Operation(summary = "Get departments")
  public List<DepartmentResponse> findAll() {
    List<DepartmentResponse> departments = departmentService.findAll();
    log.debug("Find departments");
    return departments;
  }

  @PostMapping
  @ResponseStatus(CREATED)
  @Operation(summary = "Save department")
  public DepartmentResponse saveDepartment(@RequestBody DepartmentRequest request) {
    DepartmentResponse createdDepartment = departmentService.create(request);
    log.debug("Save department");
    return createdDepartment;
  }

  @PatchMapping("/{departmentId}")
  @ResponseStatus(CREATED)
  @Operation(summary = "Updated department")
  public DepartmentResponse updateDepartment(@RequestBody DepartmentRequest request, @PathVariable Long departmentId) {
    DepartmentResponse updatedDepartment = departmentService.update(request, departmentId);
    log.debug("Update department");
    return updatedDepartment;
  }

  @DeleteMapping({"/{departmentId}"})
  @ResponseStatus(NO_CONTENT)
  @Operation(summary = "Delete department")
  public void deleteDepartment(@PathVariable Long departmentId) {
    departmentService.delete(departmentId);
    log.debug("Delete department");
  }
}
