package com.leverx.services;

import com.leverx.model.User;
import com.leverx.model.request.UserRequest;
import com.leverx.model.response.UserResponse;
import com.leverx.repositories.DepartmentRepository;
import com.leverx.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public List<UserResponse> findAll(){
        List<User> users = userRepository.findAll();
        return users.stream().map(user ->
                UserResponse.builder()
                        .userId(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .position(user.getPosition())
                        .departmentId(user.getDepartment().getId())
                        .build())
        .collect(Collectors.toList());
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

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .userId(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .position(savedUser.getPosition())
                .departmentId(savedUser.getDepartment().getId())
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

        User updatedUser = userRepository.save(userById);

        return UserResponse.builder()
                .userId(userId)
                .firstName(updatedUser.getFirstName())
                .lastName(updatedUser.getLastName())
                .position(updatedUser.getPosition())
                .departmentId(updatedUser.getDepartment().getId())
                .build();
    }

    public void delete(Long userId){
        userRepository.deleteById(userId);
    }
}
