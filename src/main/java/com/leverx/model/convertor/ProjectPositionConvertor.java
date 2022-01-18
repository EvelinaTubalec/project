package com.leverx.model.convertor;

import com.leverx.model.ProjectPosition;
import com.leverx.model.Project;
import com.leverx.model.Employee;
import com.leverx.model.dto.request.ProjectPositionRequestDto;
import com.leverx.model.dto.response.ProjectPositionResponseDto;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class ProjectPositionConvertor {

  public static ProjectPositionResponseDto toResponse(ProjectPosition projectPosition) {
    return ProjectPositionResponseDto.builder()
        .positionId(projectPosition.getId())
        .employeeId(projectPosition.getEmployee().getId())
        .projectId(projectPosition.getProject().getId())
        .positionStartDate(projectPosition.getPositionStartDate())
        .positionEndDate(projectPosition.getPositionEndDate())
        .build();
  }

  public static ProjectPosition toEntity(ProjectPositionRequestDto request, Employee employee, Project project) {
    return ProjectPosition.builder()
        .employee(employee)
        .project(project)
        .positionStartDate(request.getPositionStartDate())
        .positionEndDate(request.getPositionEndDate())
        .build();
  }

  public static ProjectPosition toEntity(ProjectPositionRequestDto request, ProjectPosition projectPosition, Employee employee, Project project) {
    projectPosition.setEmployee(employee);
    projectPosition.setProject(project);
    projectPosition.setPositionStartDate(request.getPositionStartDate());
    projectPosition.setPositionEndDate(request.getPositionEndDate());
    return projectPosition;
  }

  public static List<ProjectPositionResponseDto> toListResponse(List<ProjectPosition> projectPositions) {
    return projectPositions.stream()
        .map(
            projectPosition ->
                ProjectPositionResponseDto.builder()
                    .positionId(projectPosition.getId())
                    .employeeId(projectPosition.getEmployee().getId())
                    .projectId(projectPosition.getProject().getId())
                    .positionStartDate(projectPosition.getPositionStartDate())
                    .positionEndDate(projectPosition.getPositionEndDate())
                    .build())
        .collect(Collectors.toList());
  }
}
