package com.leverx.services;

import com.leverx.model.Position;
import com.leverx.model.dto.PositionRequest;
import com.leverx.model.response.PositionResponse;
import com.leverx.repositories.PositionRepository;
import com.leverx.repositories.ProjectRepository;
import com.leverx.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PositionService {

  private final PositionRepository positionRepository;
  private final UserRepository userRepository;
  private final ProjectRepository projectRepository;

  public List<PositionResponse> findAll() {
    List<Position> positions = positionRepository.findAll();
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

  public PositionResponse create(PositionRequest request) {
    Position userProject =
            Position.builder()
                    .user(userRepository.findById(request.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "User with id = " + request.getUserId() + " doesn't exists")))
                    .project(projectRepository.findById(request.getProjectId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Project with id = " + request.getProjectId() + " doesn't exists")))
                    .positionStartDate(request.getPositionStartDate())
                    .positionEndDate(request.getPositionEndDate())
                    .build();

    Position savedProject = positionRepository.save(userProject);

    return PositionResponse.builder()
            .positionId(userProject.getId())
            .userId(userProject.getUser().getId())
            .projectId(userProject.getProject().getId())
            .positionStartDate(savedProject.getPositionStartDate())
            .positionEndDate(savedProject.getPositionEndDate())
            .build();
  }

  public PositionResponse update(PositionRequest request, Long positionId) {
    Position positionById = positionRepository.findById(positionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Position with id = " + positionId + " doesn't exists"));
    positionById.setUser(userRepository.findById(request.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "User with id = " + request.getUserId() + " doesn't exists")));
    positionById.setProject(projectRepository.findById(request.getProjectId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Project with id = " + request.getProjectId() + " doesn't exists")));
    positionById.setPositionStartDate(request.getPositionStartDate());
    positionById.setPositionEndDate(request.getPositionEndDate());

    Position updatedPosition = positionRepository.save(positionById);

    return PositionResponse.builder()
            .positionId(updatedPosition.getId())
            .userId(updatedPosition.getUser().getId())
            .projectId(updatedPosition.getProject().getId())
            .positionStartDate(updatedPosition.getPositionStartDate())
            .positionEndDate(updatedPosition.getPositionEndDate())
            .build();
  }

  public void delete(Long positionId) {
    positionRepository.findById(positionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Position with id = " + positionId + " doesn't exists"));
    positionRepository.deleteById(positionId);
  }
}
