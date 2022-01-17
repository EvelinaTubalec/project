package com.leverx.model.convertor;

import com.leverx.model.User;
import com.leverx.model.dto.response.AvailableUserResponseDto;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
public class AvailableUserConvertor {

    public static AvailableUserResponseDto toResponse(User user, LocalDate availableDateOfUser){
        return AvailableUserResponseDto.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .position(user.getJobTitle())
                .departmentId(user.getDepartment().getId())
                .availableFrom(LocalDate.now())
                .availableTo(availableDateOfUser)
                .build();
    }
}
