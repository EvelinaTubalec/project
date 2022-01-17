package com.leverx.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {

  private String firstName;

  private String lastName;

  private String email;

  private String password;

  private String position;

  private Long departmentId;
}
