package com.leverx.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmployeeResponseDto {

  private Long employeeId;

  private String firstName;

  private String lastName;

  private String jobTitle;

  private Long departmentId;
}
