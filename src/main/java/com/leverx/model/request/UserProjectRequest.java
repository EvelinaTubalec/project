package com.leverx.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProjectRequest {

    private Long userId;

    private Long projectId;

    private LocalDate positionStartDate;

    private LocalDate positionEndDate;
}
