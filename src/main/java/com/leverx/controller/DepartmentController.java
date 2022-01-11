package com.leverx.controller;

import com.leverx.model.dto.request.DepartmentRequestDto;
import com.leverx.model.dto.response.DepartmentResponseDto;
import com.leverx.service.DepartmentService;
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
  public List<DepartmentResponseDto> findAll() {
    List<DepartmentResponseDto> departments = departmentService.findAll();
    log.debug("Find departments");
    return departments;
  }

  @PostMapping
  @ResponseStatus(CREATED)
  @Operation(summary = "Save department")
  public DepartmentResponseDto saveDepartment(@RequestBody DepartmentRequestDto request) {
    DepartmentResponseDto createdDepartment = departmentService.create(request);
    log.debug("Save department");
    return createdDepartment;
  }

  @PatchMapping("/{departmentId}")
  @ResponseStatus(OK)
  @Operation(summary = "Updated department")
  public DepartmentResponseDto updateDepartment(@RequestBody DepartmentRequestDto request, @PathVariable Long departmentId) {
    DepartmentResponseDto updatedDepartment = departmentService.update(request, departmentId);
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
