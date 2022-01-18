package com.leverx.service;

import com.leverx.model.Employee;
import com.leverx.model.convertor.AvailableUserConvertor;
import com.leverx.model.dto.response.AvailableEmployeeResponseDto;
import com.leverx.repository.EmployeeRepository;
import com.leverx.repository.ProjectPositionRepository;
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
        return findListOfAvailableUsers(LocalDate.now());
    }

    public List<AvailableEmployeeResponseDto> findAvailableInPeriodUsers(Integer period) throws ParseException {
        LocalDate date = TransformDate.addPeriodToLocalDate(period);
        return findListOfAvailableUsers(date);
    }

    public List<AvailableEmployeeResponseDto> findListOfAvailableUsers(LocalDate date){
        List<Long> availableUsers = projectPositionRepository.findAvailableEmployee(date);
        List<Employee> listOfAvailableUsers = new ArrayList<>();
        for (Long userId : availableUsers){
            Employee employee = employeeRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Employee with id = " + userId + " doesn't exists"));
            listOfAvailableUsers.add(employee);
        }
        List<AvailableEmployeeResponseDto> response = new ArrayList<>();
        for (Employee employee : listOfAvailableUsers) {
            LocalDate availableToDateOfUser = projectPositionRepository.findAvailableDateOfEmployee(employee.getId(), date);
            LocalDate availableFromDateOfUser = projectPositionRepository.findLastProjectPositionDateOfEmployee(employee.getId(), date);
            AvailableEmployeeResponseDto availableEmployeeResponseDto = AvailableUserConvertor.toResponse(employee, availableToDateOfUser, availableFromDateOfUser);
            response.add(availableEmployeeResponseDto);
        }
        return response;
    }
}
