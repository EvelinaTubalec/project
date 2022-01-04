package com.leverx.controllers;

import com.leverx.model.request.DepartmentRequest;
import com.leverx.model.request.UserRequest;
import com.leverx.model.response.DepartmentResponse;
import com.leverx.model.response.UserResponse;
import com.leverx.services.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Controller
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
    public ResponseEntity<DepartmentResponse> saveDepartment(DepartmentRequest request){
        DepartmentResponse createdDepartment = departmentService.create(request);
        return new ResponseEntity<>(createdDepartment, CREATED);
    }

    @PatchMapping()
    public ResponseEntity<DepartmentResponse> updateDepartment(DepartmentRequest request, Long departmentId){
        DepartmentResponse updatedDepartment = departmentService.update(request, departmentId);
        return new ResponseEntity<>(updatedDepartment, CREATED);
    }

    @DeleteMapping()
    public ResponseEntity deleteDepartment(Long departmentId){
        departmentService.delete(departmentId);
        return new ResponseEntity<>(OK);
    }

}
