package com.leverx.model.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPositionRequestDto {

  private Long userId;

  private Long projectId;

  private LocalDate positionStartDate;

  private LocalDate positionEndDate;
}
