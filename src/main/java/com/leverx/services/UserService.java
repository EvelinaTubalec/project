package com.leverx.services;

import com.leverx.model.Department;
import com.leverx.model.User;
import com.leverx.model.request.UserRequest;
import com.leverx.model.response.UserResponse;
import com.leverx.repositories.DepartmentRepository;
import com.leverx.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public List<UserResponse> findAll(){
        List<User> users = (List<User>) userRepository.findAll();
        List<UserResponse> result = new ArrayList<>();
        for (User u : users) {
            Department department = u.getDepartment();
            UserResponse userResponse = UserResponse.builder()
                    .userId(u.getId())
                    .firstName(u.getFirstName())
                    .lastName(u.getLastName())
                    .position(u.getPosition())
                    .departmentId(department.getId())
                    .build();
            result.add(userResponse);
        }
        return result;
    }

    public UserResponse create(UserRequest request){
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .position(request.getPosition())
                .department(departmentRepository.findDepartmentById(request.getDepartmentId()))
                .build();

        userRepository.save(user);

        return UserResponse.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .position(user.getPosition())
                .departmentId(user.getDepartment().getId())
                .build();
    }

    public UserResponse update(UserRequest request, Long userId){
        User userById = userRepository.findUserById(userId);
        userById.setFirstName(request.getFirstName());
        userById.setLastName(request.getLastName());
        userById.setEmail(request.getEmail());
        userById.setPassword(request.getPassword());
        userById.setPosition(request.getPosition());
        userById.setDepartment(departmentRepository.findDepartmentById(request.getDepartmentId()));

        userRepository.save(userById);

        return UserResponse.builder()
                .userId(userId)
                .firstName(userById.getFirstName())
                .lastName(userById.getLastName())
                .position(userById.getPosition())
                .departmentId(userById.getDepartment().getId())
                .build();
    }

    public void delete(Long userId){
        userRepository.deleteById(userId);
    }
}
