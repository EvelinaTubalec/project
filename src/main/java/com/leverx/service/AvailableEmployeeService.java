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

    public List<AvailableEmployeeResponseDto> findCurrentAvailableEmployees(){
        return findListOfAvailableEmployees(LocalDate.now());
    }

    public List<AvailableEmployeeResponseDto> findAvailableInPeriodEmployees(Integer period) throws ParseException {
        LocalDate date = TransformDate.addPeriodToLocalDate(period);
        return findListOfAvailableEmployees(date);
    }

    public List<AvailableEmployeeResponseDto> findListOfAvailableEmployees(LocalDate date){
        List<Long> availableEmployees = projectPositionRepository.findAvailableEmployee(date);
        List<Employee> listOfAvailableEmployees = new ArrayList<>();
        for (Long employeeId : availableEmployees){
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Employee with id = " + employeeId + " doesn't exists"));
            listOfAvailableEmployees.add(employee);
        }
        List<AvailableEmployeeResponseDto> response = new ArrayList<>();
        for (Employee employee : listOfAvailableEmployees) {
            LocalDate availableToDateOfEmployee = projectPositionRepository.findAvailableDateOfEmployee(employee.getId(), date);
            LocalDate availableFromDateOfEmployees = projectPositionRepository.findLastProjectPositionDateOfEmployee(employee.getId(), date);
            AvailableEmployeeResponseDto availableEmployeeResponseDto = AvailableUserConvertor.toResponse(employee, availableToDateOfEmployee, availableFromDateOfEmployees);
            response.add(availableEmployeeResponseDto);
        }
        return response;
    }
}
