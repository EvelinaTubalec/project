package com.leverx.model.utils.convertors;

import com.leverx.model.Project;
import com.leverx.model.dto.requests.ProjectRequest;
import com.leverx.model.dto.responses.ProjectResponse;
import com.leverx.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class ProjectConvertor {

  private final ProjectRepository projectRepository;

  public ProjectResponse convertProjectToResponse(Project project) {
    return ProjectResponse.builder()
        .projectId(project.getId())
        .title(project.getTitle())
        .startDate(project.getStartDate())
        .endDate(project.getEndDate())
        .build();
  }

  public Project convertRequestToProject(ProjectRequest request) {
    return Project.builder()
        .title(request.getTitle())
        .startDate(request.getStartDate())
        .endDate(request.getEndDate())
        .build();
  }

  public Project convertRequestToProject(ProjectRequest request, Long projectId) {
    Project projectById = projectRepository.findById(projectId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Project with id = " + projectId + " doesn't exists"));
    projectById.setTitle(request.getTitle());
    projectById.setStartDate(request.getStartDate());
    projectById.setEndDate(request.getEndDate());
    return projectById;
  }

  public List<ProjectResponse> convertToListProjectResponse(List<Project> projects) {
    return projects.stream()
        .map(
            project ->
                ProjectResponse.builder()
                    .projectId(project.getId())
                    .title(project.getTitle())
                    .startDate(project.getStartDate())
                    .endDate(project.getEndDate())
                    .build())
        .collect(toList());
  }
}
