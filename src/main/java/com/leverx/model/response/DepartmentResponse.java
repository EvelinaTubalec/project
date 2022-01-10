package com.leverx.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class DepartmentResponse {

  private Long departmentId;

  private String title;
}
