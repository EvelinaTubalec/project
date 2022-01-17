package com.leverx.service;

import com.leverx.model.User;
import com.leverx.model.convertor.AvailableUserConvertor;
import com.leverx.model.dto.response.AvailableUserResponseDto;
import com.leverx.repository.ProjectPositionRepository;
import com.leverx.repository.UserRepository;
import com.leverx.utils.TransformDate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AvailableUserService {

    private final ProjectPositionRepository projectPositionRepository;
    private final UserRepository userRepository;

    public List<AvailableUserResponseDto> findCurrentAvailableUsers(){
        List<Long> availableUsers = projectPositionRepository.findAvailableUser(LocalDate.now());
        return findListOfAvailableUsers(availableUsers);
    }

    public List<AvailableUserResponseDto> findAvailableInPeriodUsers(Integer period) throws ParseException {
        LocalDate date = TransformDate.addPeriodToLocalDate(period);
        List<Long> availableUsers = projectPositionRepository.findAvailableUser(date);
        return findListOfAvailableUsers(availableUsers);
    }

    public List<AvailableUserResponseDto> findListOfAvailableUsers(List<Long> availableUsers){
        List<User> result = new ArrayList<>();
        for (Long userId : availableUsers){
            User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User with id = " + userId + " doesn't exists"));
            result.add(user);
        }
        List<AvailableUserResponseDto> response = new ArrayList<>();
        for (User user : result) {
            LocalDate availableDateOfUser = projectPositionRepository.findAvailableDateOfUser(user.getId(), LocalDate.now());
            AvailableUserResponseDto availableUserResponseDto = AvailableUserConvertor.toResponse(user, availableDateOfUser);
            response.add(availableUserResponseDto);
        }
        return response;
    }
}
