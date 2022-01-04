package com.leverx.services;

import com.leverx.model.Department;
import com.leverx.model.Project;
import com.leverx.model.request.DepartmentRequest;
import com.leverx.model.request.ProjectRequest;
import com.leverx.model.response.DepartmentResponse;
import com.leverx.model.response.ProjectResponse;
import com.leverx.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<ProjectResponse> findAll(){
        List<Project> projects = (List<Project>) projectRepository.findAll();
        List<ProjectResponse> result = new ArrayList<>();
        for (Project p : projects) {
            ProjectResponse projectResponse = ProjectResponse.builder()
                    .projectId(p.getId())
                    .title(p.getTitle())
                    .startDate(p.getStartDate())
                    .endDate(p.getEndDate())
                    .build();
            result.add(projectResponse);
        }
        return result;
    }

    public ProjectResponse create(ProjectRequest request){
        Project project = Project.builder()
                .title(request.getTitle())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        projectRepository.save(project);

        return ProjectResponse.builder()
                .projectId(project.getId())
                .title(project.getTitle())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .build();
    }

    public ProjectResponse update(ProjectRequest request, Long projectId){
        Project projectById = projectRepository.findProjectById(projectId);
        projectById.setTitle(request.getTitle());
        projectById.setStartDate(request.getStartDate());
        projectById.setEndDate(request.getEndDate());

        projectRepository.save(projectById);

        return ProjectResponse.builder()
                .projectId(projectById.getId())
                .title(projectById.getTitle())
                .startDate(projectById.getStartDate())
                .endDate(projectById.getEndDate())
                .build();
    }

    public void delete(Long projectId){
        projectRepository.deleteById(projectId);
    }

}
