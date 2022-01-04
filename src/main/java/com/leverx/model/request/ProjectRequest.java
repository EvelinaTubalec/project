package com.leverx.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class ProjectRequest {

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;
}
