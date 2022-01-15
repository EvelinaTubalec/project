package com.leverx.service;

import com.leverx.model.User;
import com.leverx.model.convertor.UserConvertor;
import com.leverx.model.dto.response.UserResponseDto;
import com.leverx.repository.ProjectPositionRepository;
import com.leverx.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ForAvailableEmployeesService {

    private final ProjectPositionRepository projectPositionRepository;
    private final UserRepository userRepository;

    public List<UserResponseDto> findAvailableUsers(){
        List<Long> availableUsers = projectPositionRepository.findAvailableUser(LocalDate.now());
        List<User> result = new ArrayList<>();
        for (Long userId : availableUsers){
            User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User with id = " + userId + " doesn't exists"));
            result.add(user);
        }
        return UserConvertor.convertToListUserResponse(result);
    }

    public List<UserResponseDto> findAvailableUsers(Integer period) throws ParseException {
        String s = LocalDate.now().toString();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(format.parse(s));
        c.add(Calendar.DATE, period);  // number of days to add
        s = format.format(c.getTime());

        Date docDate= format.parse(s);

        LocalDate date = docDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate.now();
        List<Long> availableUsers = projectPositionRepository.findAvailableUser(date);
        List<User> result = new ArrayList<>();
        for (Long userId : availableUsers){
            User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User with id = " + userId + " doesn't exists"));
            result.add(user);
        }
        return UserConvertor.convertToListUserResponse(result);
    }
}
