package com.leverx.model.convertor;

import com.leverx.model.Position;
import com.leverx.model.Project;
import com.leverx.model.User;
import com.leverx.model.dto.request.PositionRequestDto;
import com.leverx.model.dto.response.PositionResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class PositionConvertor {

  public PositionConvertor() {
  }

  public static PositionResponseDto toResponse(Position position) {
    return PositionResponseDto.builder()
        .positionId(position.getId())
        .userId(position.getUser().getId())
        .projectId(position.getProject().getId())
        .positionStartDate(position.getPositionStartDate())
        .positionEndDate(position.getPositionEndDate())
        .build();
  }

  public static Position toEntity(PositionRequestDto request, User user, Project project) {
    return Position.builder()
        .user(user)
        .project(project)
        .positionStartDate(request.getPositionStartDate())
        .positionEndDate(request.getPositionEndDate())
        .build();
  }

  public static Position toEntity(PositionRequestDto request, Position position, User user, Project project) {
    position.setUser(user);
    position.setProject(project);
    position.setPositionStartDate(request.getPositionStartDate());
    position.setPositionEndDate(request.getPositionEndDate());
    return position;
  }

  public static List<PositionResponseDto> toListResponse(List<Position> positions) {
    return positions.stream()
        .map(
            userProject ->
                PositionResponseDto.builder()
                    .positionId(userProject.getId())
                    .userId(userProject.getUser().getId())
                    .projectId(userProject.getProject().getId())
                    .positionStartDate(userProject.getPositionStartDate())
                    .positionEndDate(userProject.getPositionEndDate())
                    .build())
        .collect(Collectors.toList());
  }
}
