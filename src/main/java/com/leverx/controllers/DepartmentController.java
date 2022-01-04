package com.leverx.controllers;

import com.leverx.model.request.DepartmentRequest;
import com.leverx.model.response.DepartmentResponse;
import com.leverx.services.DepartmentService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping()
    public ResponseEntity<List<DepartmentResponse>> findAll(){
        List<DepartmentResponse> departments = departmentService.findAll();
        return new ResponseEntity<>(departments, OK);
    }

    @PostMapping()
    public ResponseEntity<DepartmentResponse> saveDepartment(@RequestBody DepartmentRequest request){
        DepartmentResponse createdDepartment = departmentService.create(request);
        return new ResponseEntity<>(createdDepartment, CREATED);
    }

    @PatchMapping("{departmentId}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@RequestBody DepartmentRequest request, @PathVariable Long departmentId){
        DepartmentResponse updatedDepartment = departmentService.update(request, departmentId);
        return new ResponseEntity<>(updatedDepartment, CREATED);
    }

    @DeleteMapping({"{departmentId}"})
    public ResponseEntity deleteDepartment(@PathVariable Long departmentId){
        departmentService.delete(departmentId);
        return new ResponseEntity<>(OK);
    }
}
