package com.leverx.controller;

import com.leverx.model.Employee;
import com.leverx.model.dto.request.EmployeeRequestDto;
import com.leverx.model.dto.response.AvailableEmployeeResponseDto;
import com.leverx.model.dto.response.EmployeeResponseDto;
import com.leverx.report.WriteExcelReport;
import com.leverx.service.AvailableEmployeeService;
import com.leverx.service.EmployeeService;
import com.leverx.service.LoadingEmployeesFromCSVFileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

  private final EmployeeService employeeService;
  private final LoadingEmployeesFromCSVFileService loadingEmployeesFromCSVFileService;
  private final AvailableEmployeeService availableEmployeeService;
  public final WriteExcelReport writeExcelReport;
  private static final Logger log = LoggerFactory.logger(DepartmentController.class);

  @GetMapping("/statistic")
  @ResponseStatus(OK)
  @Operation(summary = "Get users statistic")
  public void getStatistic() {
    log.debug("Get users statistic");
    writeExcelReport.writeToXmlFileStatisticOfUsersForMonth();
  }

  @GetMapping("/statistic2")
  @ResponseStatus(OK)
  @Operation(summary = "Get statistic of available employees")
  public void getStatisticOfAvailableEmployees() throws ParseException {
    log.debug("Get statistic of available employees");
    writeExcelReport.writeToXmlFileAvailableEmployeesDuringMonth();
  }

  @GetMapping("/csv")
  @ResponseStatus(OK)
  @Operation(summary = "Get employees from CSV file")
  public List<Employee> findAllFromCSVFile() throws FileNotFoundException {
    log.debug("Get employees from CSV file");
    return loadingEmployeesFromCSVFileService.findAllFromCSVFile();
  }

  @GetMapping("/available")
  @ResponseStatus(OK)
  @Operation(summary = "Get available employees")
  public List<AvailableEmployeeResponseDto> findAllAvailableEmployees(){
    log.debug("Get available employees");
    return availableEmployeeService.findCurrentAvailableUsers();
  }

  @GetMapping("/available/{period}")
  @ResponseStatus(OK)
  @Operation(summary = "Get available employees")
  public List<AvailableEmployeeResponseDto> findAllAvailableEmployeeInPeriod(@PathVariable("period") Integer period) throws ParseException {
    log.debug("Get available employees in period");
    return availableEmployeeService.findAvailableInPeriodUsers(period);
  }

  @GetMapping
  @ResponseStatus(OK)
  @Operation(summary = "Get employees")
  public List<EmployeeResponseDto> findAll() {
    List<EmployeeResponseDto> users = employeeService.findAll();
    log.debug("Find employees");
    return users;
  }

  @PostMapping
  @ResponseStatus(CREATED)
  @Operation(summary = "Save employee")
  public EmployeeResponseDto saveEmployee(@RequestBody EmployeeRequestDto request) {
    EmployeeResponseDto createdUser = employeeService.create(request);
    log.debug("Save employee");
    return createdUser;
  }

  @PatchMapping("/{employeesId}")
  @ResponseStatus(OK)
  @Operation(summary = "Update employee")
  public EmployeeResponseDto updateEmployee(
          @RequestBody EmployeeRequestDto request, @PathVariable Long employeesId) {
    EmployeeResponseDto updatedUser = employeeService.update(request, employeesId);
    log.debug("Update employee");
    return updatedUser;
  }

  @DeleteMapping("/{employeesId}")
  @ResponseStatus(NO_CONTENT)
  @Operation(summary = "Delete employee")
  public void deleteEmployee(@PathVariable Long employeesId) {
    employeeService.delete(employeesId);
    log.debug("Delete employee");
  }
}
