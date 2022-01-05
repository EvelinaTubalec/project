package com.leverx.services;

import com.leverx.model.UserProject;
import com.leverx.model.request.UserProjectRequest;
import com.leverx.model.response.UserProjectResponse;
import com.leverx.repositories.ProjectRepository;
import com.leverx.repositories.UserProjectRepository;
import com.leverx.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserProjectService {

    private final UserProjectRepository userProjectRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public List<UserProjectResponse> findAll(){
        List<UserProject> userProjects = userProjectRepository.findAll();
        return userProjects.stream().map(userProject ->
                UserProjectResponse.builder()
                        .userProjectId(userProject.getId())
                        .userId(userProject.getUser().getId())
                        .projectId(userProject.getProject().getId())
                        .positionStartDate(userProject.getPositionStartDate())
                        .positionEndDate(userProject.getPositionEndDate())
                        .build())
                .collect(Collectors.toList());
    }

    public UserProjectResponse create(UserProjectRequest request){
        UserProject userProject = UserProject.builder()
                .user(userRepository.findUserById(request.getUserId()))
                .project(projectRepository.findProjectById(request.getProjectId()))
                .positionStartDate(request.getPositionStartDate())
                .positionEndDate(request.getPositionEndDate())
                .build();

        UserProject savedUserProject = userProjectRepository.save(userProject);

        return UserProjectResponse.builder()
                .userProjectId(userProject.getId())
                .userId(userProject.getUser().getId())
                .projectId(userProject.getProject().getId())
                .positionStartDate(savedUserProject.getPositionStartDate())
                .positionEndDate(savedUserProject.getPositionEndDate())
                .build();
    }

    public UserProjectResponse update(UserProjectRequest request, Long userProjectId){
        UserProject userProjectById = userProjectRepository.findUserProjectById(userProjectId);
        userProjectById.setUser(userRepository.findUserById(request.getUserId()));
        userProjectById.setProject(projectRepository.findProjectById(request.getProjectId()));
        userProjectById.setPositionStartDate(request.getPositionStartDate());
        userProjectById.setPositionEndDate(request.getPositionEndDate());

        UserProject updatedUserProject = userProjectRepository.save(userProjectById);

        return UserProjectResponse.builder()
                .userProjectId(updatedUserProject.getId())
                .userId(updatedUserProject.getUser().getId())
                .projectId(updatedUserProject.getProject().getId())
                .positionStartDate(updatedUserProject.getPositionStartDate())
                .positionEndDate(updatedUserProject.getPositionEndDate())
                .build();
    }

    public void delete(Long userProjectId){
        userProjectRepository.deleteById(userProjectId);
    }
}
