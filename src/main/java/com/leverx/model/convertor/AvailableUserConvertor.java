package com.leverx.model.convertor;

import com.leverx.model.Employee;
import com.leverx.model.dto.response.AvailableEmployeeResponseDto;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
public class AvailableUserConvertor {

    public static AvailableEmployeeResponseDto toResponse(Employee employee, LocalDate availableToDateOfUser, LocalDate availableFromDateOfUser){

        return AvailableEmployeeResponseDto.builder()
                .employeeId(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .position(employee.getJobTitle())
                .departmentId(employee.getDepartment().getId())
                .availableFrom(availableFromDateOfUser)
                .availableTo(availableToDateOfUser)
                .build();
    }
}
