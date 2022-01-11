package com.leverx.service;

import com.leverx.model.Position;
import com.leverx.model.Project;
import com.leverx.model.User;
import com.leverx.model.dto.request.PositionRequestDto;
import com.leverx.model.dto.response.PositionResponseDto;
import com.leverx.model.convertor.PositionConvertor;
import com.leverx.repository.PositionRepository;
import com.leverx.repository.ProjectRepository;
import com.leverx.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class PositionService {

  private final PositionRepository positionRepository;
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  public List<PositionResponseDto> findAll() {
    List<Position> positions = positionRepository.findAll();
    return PositionConvertor.toListResponse(positions);
  }

  public PositionResponseDto create(PositionRequestDto request) {
    User userById = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "User with id = " + request.getUserId() + " doesn't exists"));
    Project projectById = projectRepository.findById(request.getProjectId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Project with id = " + request.getProjectId() + " doesn't exists"));
    Position position = PositionConvertor.toEntity(request, userById, projectById);
    Position savedProject = positionRepository.save(position);
    return PositionConvertor.toResponse(savedProject);
  }

  public PositionResponseDto update(PositionRequestDto request, Long positionId) {
    Position positionById = positionRepository.findById(positionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Position with id = " + positionId + " doesn't exists"));
    User userById = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "User with id = " + request.getUserId() + " doesn't exists"));
    Project projectById = projectRepository.findById(request.getProjectId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Project with id = " + request.getProjectId() + " doesn't exists"));
    Position position = PositionConvertor.toEntity(request, positionById, userById, projectById);
    Position updatedPosition = positionRepository.save(position);
    return PositionConvertor.toResponse(updatedPosition);
  }

  public void delete(Long positionId) {
    positionRepository.findById(positionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Position with id = " + positionId + " doesn't exists"));
    positionRepository.deleteById(positionId);
  }
}
