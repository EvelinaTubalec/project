package com.leverx.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class UserProjectResponse {

    private Long userProjectId;

    private Long userId;

    private Long projectId;

    private LocalDate positionStartDate;

    private LocalDate positionEndDate;
}
