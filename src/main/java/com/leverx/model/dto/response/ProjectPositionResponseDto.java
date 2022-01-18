package com.leverx.model.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProjectPositionResponseDto {

  private Long positionId;

  private Long employeeId;

  private Long projectId;

  private LocalDate positionStartDate;

  private LocalDate positionEndDate;
}
