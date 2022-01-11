package com.leverx.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DepartmentResponseDto {

  private Long departmentId;

  private String title;
}
