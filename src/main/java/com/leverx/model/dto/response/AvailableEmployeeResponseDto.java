package com.leverx.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class AvailableEmployeeResponseDto {

    private Long employeeId;

    private String firstName;

    private String lastName;

    private String position;

    private Long departmentId;

    private LocalDate availableFrom;

    private LocalDate availableTo;
}
