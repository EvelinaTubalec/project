package com.leverx.services;

import com.leverx.model.UserProject;
import com.leverx.model.request.UserProjectRequest;
import com.leverx.model.response.UserProjectResponse;
import com.leverx.repositories.UserProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserProjectService {

    private final UserProjectRepository userProjectRepository;

    public List<UserProjectResponse> findAll(){
        List<UserProject> userProjects = userProjectRepository.findAll();
        return userProjects.stream().map(userProject ->
                UserProjectResponse.builder()
                        .userProjectId(userProject.getProjectId())
                        .userId(userProject.getUserId())
                        .projectId(userProject.getProjectId())
                        .positionStartDate(userProject.getPositionStartDate())
                        .positionEndDate(userProject.getPositionEndDate())
                        .build())
                .collect(Collectors.toList());
    }

    public UserProjectResponse create(UserProjectRequest request){
        UserProject userProject = UserProject.builder()
                .userId(request.getUserId())
                .projectId(request.getProjectId())
                .positionStartDate(request.getPositionStartDate())
                .positionEndDate(request.getPositionEndDate())
                .build();

        UserProject savedUserProject = userProjectRepository.save(userProject);

        return UserProjectResponse.builder()
                .userProjectId(savedUserProject.getProjectId())
                .userId(savedUserProject.getUserId())
                .projectId(savedUserProject.getProjectId())
                .positionStartDate(savedUserProject.getPositionStartDate())
                .positionEndDate(savedUserProject.getPositionEndDate())
                .build();
    }

    public UserProjectResponse update(UserProjectRequest request, Long userProjectId){
        UserProject userProjectById = userProjectRepository.findUserProjectById(userProjectId);
        userProjectById.setUserId(request.getUserId());
        userProjectById.setProjectId(request.getProjectId());
        userProjectById.setPositionStartDate(request.getPositionStartDate());
        userProjectById.setPositionEndDate(request.getPositionEndDate());

        UserProject updatedUserProject = userProjectRepository.save(userProjectById);

        return UserProjectResponse.builder()
                .userProjectId(updatedUserProject.getProjectId())
                .userId(updatedUserProject.getUserId())
                .projectId(updatedUserProject.getProjectId())
                .positionStartDate(updatedUserProject.getPositionStartDate())
                .positionEndDate(updatedUserProject.getPositionEndDate())
                .build();
    }

    public void delete(Long userProjectId){
        userProjectRepository.deleteById(userProjectId);
    }
}
