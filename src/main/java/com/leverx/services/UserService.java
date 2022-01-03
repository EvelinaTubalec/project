package com.leverx.services;

import com.leverx.model.Department;
import com.leverx.model.User;
import com.leverx.model.request.UserRequest;
import com.leverx.model.response.UserResponse;
import com.leverx.repositories.DepartmentRepository;
import com.leverx.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public UserService(UserRepository userRepository, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    public User findUserById(Long userId){
        return userRepository.findUserById(userId);
    }

    public List<UserResponse> findAll(){
        List<User> users = (List<User>) userRepository.findAll();
        List<UserResponse> result = new ArrayList<>();
        for (User u : users) {
            Department department = u.getDepartment();
            UserResponse userResponse = UserResponse.builder()
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
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .position(request.getPosition())
                .departmentId(request.getDepartmentId())
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

        return UserResponse.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .position(request.getPosition())
                .departmentId(request.getDepartmentId())
                .build();
    }

    public void delete(Long userId){
        userRepository.deleteById(userId);
    }
}
