package com.leverx.services;

import static java.util.stream.Collectors.toList;

import com.leverx.model.Project;
import com.leverx.model.request.ProjectRequest;
import com.leverx.model.response.ProjectResponse;
import com.leverx.repositories.ProjectRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;

  public List<ProjectResponse> findAll() {
    List<Project> projects = projectRepository.findAll();
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

  public ProjectResponse create(ProjectRequest request) {
    Project project =
        Project.builder()
            .title(request.getTitle())
            .startDate(request.getStartDate())
            .endDate(request.getEndDate())
            .build();

    Project savedProject = projectRepository.save(project);

    return ProjectResponse.builder()
        .projectId(savedProject.getId())
        .title(savedProject.getTitle())
        .startDate(savedProject.getStartDate())
        .endDate(savedProject.getEndDate())
        .build();
  }

  public ProjectResponse update(ProjectRequest request, Long projectId) {
    Project projectById = projectRepository.findProjectById(projectId);
    projectById.setTitle(request.getTitle());
    projectById.setStartDate(request.getStartDate());
    projectById.setEndDate(request.getEndDate());

    Project updatedProject = projectRepository.save(projectById);

    return ProjectResponse.builder()
        .projectId(updatedProject.getId())
        .title(updatedProject.getTitle())
        .startDate(updatedProject.getStartDate())
        .endDate(updatedProject.getEndDate())
        .build();
  }

  public void delete(Long projectId) {
    projectRepository.deleteById(projectId);
  }
}
