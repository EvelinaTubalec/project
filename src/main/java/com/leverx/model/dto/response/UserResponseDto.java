package com.leverx.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDto {

  private Long userId;

  private String firstName;

  private String lastName;

  private String position;

  private Long departmentId;
}