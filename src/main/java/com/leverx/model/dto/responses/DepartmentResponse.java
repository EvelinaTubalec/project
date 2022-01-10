package com.leverx.model.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DepartmentResponse {

  private Long departmentId;

  private String title;
}
