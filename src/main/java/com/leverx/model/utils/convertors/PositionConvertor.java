package com.leverx.model.utils.convertors;

import com.leverx.model.Position;
import com.leverx.model.dto.requests.PositionRequest;
import com.leverx.model.dto.responses.PositionResponse;
import com.leverx.repositories.PositionRepository;
import com.leverx.repositories.ProjectRepository;
import com.leverx.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PositionConvertor {

  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;
  private final PositionRepository positionRepository;

  public PositionResponse convertPositionToResponse(Position position) {
    return PositionResponse.builder()
        .positionId(position.getId())
        .userId(position.getUser().getId())
        .projectId(position.getProject().getId())
        .positionStartDate(position.getPositionStartDate())
        .positionEndDate(position.getPositionEndDate())
        .build();
  }

  public Position convertRequestToPosition(PositionRequest request) {
    return Position.builder()
        .user(
            userRepository
                .findById(request.getUserId())
                .orElseThrow(
                    () ->
                        new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "User with id = " + request.getUserId() + " doesn't exists")))
        .project(
            projectRepository
                .findById(request.getProjectId())
                .orElseThrow(
                    () ->
                        new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Project with id = " + request.getProjectId() + " doesn't exists")))
        .positionStartDate(request.getPositionStartDate())
        .positionEndDate(request.getPositionEndDate())
        .build();
  }

  public Position convertRequestToPosition(PositionRequest request, Long positionId) {
    Position positionById = positionRepository.findById(positionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Position with id = " + positionId + " doesn't exists"));
    positionById.setUser(userRepository.findById(request.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "User with id = " + request.getUserId() + " doesn't exists")));
    positionById.setProject(projectRepository.findById(request.getProjectId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Project with id = " + request.getProjectId() + " doesn't exists")));
    positionById.setPositionStartDate(request.getPositionStartDate());
    positionById.setPositionEndDate(request.getPositionEndDate());
    return positionById;
  }

  public List<PositionResponse> convertToListPositionResponse(List<Position> positions) {
    return positions.stream()
        .map(
            userProject ->
                PositionResponse.builder()
                    .positionId(userProject.getId())
                    .userId(userProject.getUser().getId())
                    .projectId(userProject.getProject().getId())
                    .positionStartDate(userProject.getPositionStartDate())
                    .positionEndDate(userProject.getPositionEndDate())
                    .build())
        .collect(Collectors.toList());
  }
}
