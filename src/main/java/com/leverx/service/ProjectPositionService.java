package com.leverx.service;

import com.leverx.model.ProjectPosition;
import com.leverx.model.Project;
import com.leverx.model.User;
import com.leverx.model.dto.request.ProjectPositionRequestDto;
import com.leverx.model.dto.response.ProjectPositionResponseDto;
import com.leverx.model.convertor.ProjectPositionConvertor;
import com.leverx.repository.ProjectPositionRepository;
import com.leverx.repository.ProjectRepository;
import com.leverx.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectPositionService {

  private final ProjectPositionRepository projectPositionRepository;
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  public List<ProjectPositionResponseDto> findAll() {
    List<ProjectPosition> projectPositions = projectPositionRepository.findAll();
    return ProjectPositionConvertor.toListResponse(projectPositions);
  }

  public ProjectPositionResponseDto create(ProjectPositionRequestDto request) {
    User userById = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "User with id = " + request.getUserId() + " doesn't exists"));
    Project projectById = projectRepository.findById(request.getProjectId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Project with id = " + request.getProjectId() + " doesn't exists"));
    ProjectPosition projectPosition = ProjectPositionConvertor.toEntity(request, userById, projectById);

    if (projectPositionRepository.findProjectPositionIdByUserId(userById.getId()) != null){
      List<Long> projectPositionByUserId = projectPositionRepository.findProjectPositionIdByUserId(userById.getId());
      for (Long projectPositionId: projectPositionByUserId) {
        ProjectPosition actualProjectPosition = projectPositionRepository.findById(projectPositionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Project with id = " + request.getProjectId() + " doesn't exists"));
        if(actualProjectPosition.getPositionEndDate().isAfter(LocalDate.now()) || actualProjectPosition.getPositionEndDate().isEqual(LocalDate.now())){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user already has current project position");
        }
      }
    }
      ProjectPosition savedProject = projectPositionRepository.save(projectPosition);
      return ProjectPositionConvertor.toResponse(savedProject);
  }

  public ProjectPositionResponseDto update(ProjectPositionRequestDto request, Long positionId) {
    ProjectPosition projectPositionById = projectPositionRepository.findById(positionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Position with id = " + positionId + " doesn't exists"));
    User userById = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "User with id = " + request.getUserId() + " doesn't exists"));
    Project projectById = projectRepository.findById(request.getProjectId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Project with id = " + request.getProjectId() + " doesn't exists"));
    ProjectPosition projectPosition = ProjectPositionConvertor.toEntity(request, projectPositionById, userById, projectById);
    ProjectPosition updatedProjectPosition = projectPositionRepository.save(projectPosition);
    return ProjectPositionConvertor.toResponse(updatedProjectPosition);
  }

  public void delete(Long positionId) {
    projectPositionRepository.findById(positionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Position with id = " + positionId + " doesn't exists"));
    projectPositionRepository.deleteById(positionId);
  }
}
