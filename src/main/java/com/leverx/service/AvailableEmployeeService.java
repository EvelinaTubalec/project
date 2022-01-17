package com.leverx.service;

import com.leverx.model.Employee;
import com.leverx.model.convertor.AvailableUserConvertor;
import com.leverx.model.dto.response.AvailableEmployeeResponseDto;
import com.leverx.repository.ProjectPositionRepository;
import com.leverx.repository.EmployeeRepository;
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
public class AvailableEmployeeService {

    private final ProjectPositionRepository projectPositionRepository;
    private final EmployeeRepository employeeRepository;

    public List<AvailableEmployeeResponseDto> findCurrentAvailableUsers(){
        List<Long> availableUsers = projectPositionRepository.findAvailableUser(LocalDate.now());
        return findListOfAvailableUsers(availableUsers);
    }

    public List<AvailableEmployeeResponseDto> findAvailableInPeriodUsers(Integer period) throws ParseException {
        LocalDate date = TransformDate.addPeriodToLocalDate(period);
        List<Long> availableUsers = projectPositionRepository.findAvailableUser(date);
        return findListOfAvailableUsers(availableUsers);
    }

    public List<AvailableEmployeeResponseDto> findListOfAvailableUsers(List<Long> availableUsers){
        List<Employee> result = new ArrayList<>();
        for (Long userId : availableUsers){
            Employee employee = employeeRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Employee with id = " + userId + " doesn't exists"));
            result.add(employee);
        }
        List<AvailableEmployeeResponseDto> response = new ArrayList<>();
        for (Employee employee : result) {
            LocalDate availableDateOfUser = projectPositionRepository.findAvailableDateOfUser(employee.getId(), LocalDate.now());
            AvailableEmployeeResponseDto availableEmployeeResponseDto = AvailableUserConvertor.toResponse(employee, availableDateOfUser);
            response.add(availableEmployeeResponseDto);
        }
        return response;
    }
}
