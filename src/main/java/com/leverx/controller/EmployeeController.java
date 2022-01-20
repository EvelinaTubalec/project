package com.leverx.controller;

import com.leverx.model.Employee;
import com.leverx.model.dto.request.EmployeeRequestDto;
import com.leverx.model.dto.response.AvailableEmployeeResponseDto;
import com.leverx.model.dto.response.EmployeeResponseDto;
import com.leverx.service.EmployeeService;
import com.leverx.service.LoadingFromCSVFileService;
import com.leverx.service.ReportService;
import com.leverx.utils.TransformDate;
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
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

  private final EmployeeService employeeService;
  private final ReportService reportService;
  private final LoadingFromCSVFileService loadingFromCSVFileService;
  private static final Logger log = LoggerFactory.logger(DepartmentController.class);

  @GetMapping("/statistic_report")
  @ResponseStatus(OK)
  @Operation(summary = "Get statistic report about employees during month")
  public String getStatistic() {
    return reportService.getStatisticForMonth();
  }

  @GetMapping("/report_of_available_employees")
  @ResponseStatus(OK)
  @Operation(summary = "Get report of available employees during month")
  public String getStatisticOfAvailableEmployees() throws ParseException {
    return reportService.replaceReportOfAvailableEmployees();
  }

  @GetMapping("/last_report")
  @ResponseStatus(OK)
  @Operation(summary = "Get last report")
  public String getLastReport() {
    return reportService.getLastReport();
  }

  @GetMapping("/loading-from-csv-file")
  @ResponseStatus(OK)
  @Operation(summary = "Get employees from CSV file")
  public List<Employee> findAllFromCSVFile() throws FileNotFoundException {
    log.debug("Get employees from CSV file");
    return loadingFromCSVFileService.findAllFromCSVFile();
  }

  @GetMapping("/available")
  @ResponseStatus(OK)
  @Operation(summary = "Get available employees")
  public List<AvailableEmployeeResponseDto> findAllAvailableEmployees(){
    log.debug("Get available employees");
    return employeeService.findListOfAvailableEmployees(LocalDate.now());
  }

  @GetMapping("/available/{period}")
  @ResponseStatus(OK)
  @Operation(summary = "Get available employees in period")
  public List<AvailableEmployeeResponseDto> findAllAvailableEmployeeInPeriod(@PathVariable("period") Integer period) throws ParseException {
    LocalDate date = TransformDate.addPeriodToLocalDate(period);
    log.debug("Get available employees in period");
    return employeeService.findListOfAvailableEmployees(date);
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
