package com.leverx.model.dto.responses;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PositionResponse {

  private Long positionId;

  private Long userId;

  private Long projectId;

  private LocalDate positionStartDate;

  private LocalDate positionEndDate;
}
