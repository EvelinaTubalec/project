package com.leverx.model.convertor;

import com.leverx.model.Project;
import com.leverx.model.dto.request.ProjectRequestDto;
import com.leverx.model.dto.response.ProjectResponseDto;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ProjectConvertor {

  public ProjectConvertor() {
  }

  public static ProjectResponseDto toResponse(Project project) {
    return ProjectResponseDto.builder()
        .projectId(project.getId())
        .title(project.getTitle())
        .startDate(project.getStartDate())
        .endDate(project.getEndDate())
        .build();
  }

  public static Project toEntity(ProjectRequestDto request) {
    return Project.builder()
        .title(request.getTitle())
        .startDate(request.getStartDate())
        .endDate(request.getEndDate())
        .build();
  }

  public static Project toEntity(ProjectRequestDto request, Project project) {
    project.setTitle(request.getTitle());
    project.setStartDate(request.getStartDate());
    project.setEndDate(request.getEndDate());
    return project;
  }

  public static List<ProjectResponseDto> toListResponse(List<Project> projects) {
    return projects.stream()
        .map(
            project ->
                ProjectResponseDto.builder()
                    .projectId(project.getId())
                    .title(project.getTitle())
                    .startDate(project.getStartDate())
                    .endDate(project.getEndDate())
                    .build())
        .collect(toList());
  }
}
