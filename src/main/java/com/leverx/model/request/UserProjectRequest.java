package com.leverx.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class UserProjectRequest {

    private Long userId;

    private Long projectId;

    private LocalDate positionStartDate;

    private LocalDate positionEndDate;
}
