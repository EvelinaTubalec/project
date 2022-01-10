package com.leverx.services;

import com.leverx.model.Project;
import com.leverx.model.dto.requests.ProjectRequest;
import com.leverx.model.dto.responses.ProjectResponse;
import com.leverx.model.utils.convertors.ProjectConvertor;
import com.leverx.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectConvertor projectConvertor;

  public List<ProjectResponse> findAll() {
    List<Project> projects = projectRepository.findAll();
    return projectConvertor.convertToListProjectResponse(projects);
  }

  public ProjectResponse create(ProjectRequest request) {
    Project project = projectConvertor.convertRequestToProject(request);
    Project savedProject = projectRepository.save(project);
    return projectConvertor.convertProjectToResponse(savedProject);
  }

  public ProjectResponse update(ProjectRequest request, Long projectId) {
    Project project = projectConvertor.convertRequestToProject(request, projectId);
    Project updatedProject = projectRepository.save(project);
    return projectConvertor.convertProjectToResponse(updatedProject);
  }

  public void delete(Long projectId) {
    projectRepository.findById(projectId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Project with id = " + projectId + " doesn't exists"));
    projectRepository.deleteById(projectId);
  }
}
