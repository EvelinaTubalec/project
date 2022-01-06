package com.leverx.model.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProjectResponse {

  private Long projectId;

  private String title;

  private LocalDate startDate;

  private LocalDate endDate;
}
