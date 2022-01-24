package com.leverx.service;

import com.leverx.model.Project;
import com.leverx.model.convertor.ProjectConvertor;
import com.leverx.model.dto.request.ProjectRequestDto;
import com.leverx.model.dto.response.ProjectResponseDto;
import com.leverx.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.leverx.model.convertor.ProjectConvertor.toListResponse;

@Service
@AllArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;

  public List<ProjectResponseDto> findAll() {
    List<Project> projects = projectRepository.findAll();
    return toListResponse(projects);
  }

  public ProjectResponseDto create(ProjectRequestDto request) {
    validateProjectRequest(request);

    Project project = ProjectConvertor.toEntity(request);
    Project savedProject = projectRepository.save(project);
    return ProjectConvertor.toResponse(savedProject);
  }

  private void validateProjectRequest(ProjectRequestDto request) {
    if(StringUtils.isEmpty(request.getTitle()) ||
            request.getStartDate() == null ||
            request.getEndDate() == null){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "Field(s) is empty");
    }
  }

  public ProjectResponseDto update(ProjectRequestDto request, Long projectId) {
    Project projectById = projectRepository.findById(projectId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Project with id = " + projectId + " doesn't exists"));
    Project project = ProjectConvertor.toEntity(request, projectById);
    Project updatedProject = projectRepository.save(project);
    return ProjectConvertor.toResponse(updatedProject);
  }

  public void delete(Long projectId) {
    projectRepository.findById(projectId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Project with id = " + projectId + " doesn't exists"));
    projectRepository.deleteById(projectId);
  }
}
