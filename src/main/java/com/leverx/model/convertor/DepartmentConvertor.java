package com.leverx.model.convertor;

import com.leverx.model.Department;
import com.leverx.model.dto.request.DepartmentRequestDto;
import com.leverx.model.dto.response.DepartmentResponseDto;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
public class DepartmentConvertor {

    public static DepartmentResponseDto toResponse(Department department){
        return DepartmentResponseDto.builder()
                .departmentId(department.getId())
                .title(department.getTitle())
                .build();
    }

    public static Department toEntity(DepartmentRequestDto request){
        return Department
                .builder()
                .title(request.getTitle())
                .build();
    }

    public static Department toEntity(DepartmentRequestDto request, Department departmentById){
        departmentById.setTitle(request.getTitle());
        return departmentById;
    }

    public static List<DepartmentResponseDto> toListResponse(List<Department> departments){
        return departments.stream()
                .map(
                        department ->
                                DepartmentResponseDto.builder()
                                        .departmentId(department.getId())
                                        .title(department.getTitle())
                                        .build())
                .collect(toList());
    }
}
