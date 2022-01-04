package com.leverx.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class ProjectResponse {

    private Long projectId;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;
}
