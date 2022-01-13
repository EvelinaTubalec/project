package com.leverx.model.convertor;

import com.leverx.model.ProjectPosition;
import com.leverx.model.Project;
import com.leverx.model.User;
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
        .userId(projectPosition.getUser().getId())
        .projectId(projectPosition.getProject().getId())
        .positionStartDate(projectPosition.getPositionStartDate())
        .positionEndDate(projectPosition.getPositionEndDate())
        .build();
  }

  public static ProjectPosition toEntity(ProjectPositionRequestDto request, User user, Project project) {
    return ProjectPosition.builder()
        .user(user)
        .project(project)
        .positionStartDate(request.getPositionStartDate())
        .positionEndDate(request.getPositionEndDate())
        .build();
  }

  public static ProjectPosition toEntity(ProjectPositionRequestDto request, ProjectPosition projectPosition, User user, Project project) {
    projectPosition.setUser(user);
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
                    .userId(projectPosition.getUser().getId())
                    .projectId(projectPosition.getProject().getId())
                    .positionStartDate(projectPosition.getPositionStartDate())
                    .positionEndDate(projectPosition.getPositionEndDate())
                    .build())
        .collect(Collectors.toList());
  }
}
