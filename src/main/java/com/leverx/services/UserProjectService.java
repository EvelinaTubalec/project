package com.leverx.services;

import com.leverx.model.UserProject;
import com.leverx.model.request.UserProjectRequest;
import com.leverx.model.response.UserProjectResponse;
import com.leverx.repositories.DepartmentRepository;
import com.leverx.repositories.UserProjectRepository;
import com.leverx.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserProjectService {

    private final UserProjectRepository userProjectRepository;

    public List<UserProjectResponse> findAll(){
        List<UserProject> userProjects = (List<UserProject>) userProjectRepository.findAll();
        List<UserProjectResponse> result = new ArrayList<>();
        for (UserProject userProject : userProjects) {
            UserProjectResponse userProjectResponse = UserProjectResponse.builder()
                    .userProjectId(userProject.getProjectId())
                    .userId(userProject.getUserId())
                    .projectId(userProject.getProjectId())
                    .positionStartDate(userProject.getPositionStartDate())
                    .positionEndDate(userProject.getPositionEndDate())
                    .build();
            result.add(userProjectResponse);
        }
        return result;
    }

    public UserProjectResponse create(UserProjectRequest request){
        UserProject userProject = UserProject.builder()
                .userId(request.getUserId())
                .projectId(request.getProjectId())
                .positionStartDate(request.getPositionStartDate())
                .positionEndDate(request.getPositionEndDate())
                .build();

        userProjectRepository.save(userProject);

        return UserProjectResponse.builder()
                .userProjectId(userProject.getProjectId())
                .userId(userProject.getUserId())
                .projectId(userProject.getProjectId())
                .positionStartDate(userProject.getPositionStartDate())
                .positionEndDate(userProject.getPositionEndDate())
                .build();
    }

    public UserProjectResponse update(UserProjectRequest request, Long userProjectId){
        UserProject userProjectById = userProjectRepository.findUserProjectById(userProjectId);
        userProjectById.setUserId(request.getUserId());
        userProjectById.setProjectId(request.getProjectId());
        userProjectById.setPositionStartDate(request.getPositionStartDate());
        userProjectById.setPositionEndDate(request.getPositionEndDate());

        userProjectRepository.save(userProjectById);

        return UserProjectResponse.builder()
                .userProjectId(userProjectById.getProjectId())
                .userId(userProjectById.getUserId())
                .projectId(userProjectById.getProjectId())
                .positionStartDate(userProjectById.getPositionStartDate())
                .positionEndDate(userProjectById.getPositionEndDate())
                .build();
    }

    public void delete(Long userProjectId){
        userProjectRepository.deleteById(userProjectId);
    }
}
