package com.leverx.report;

import com.leverx.model.Department;
import com.leverx.model.Employee;
import com.leverx.model.Project;
import com.leverx.model.ProjectPosition;
import com.leverx.repository.DepartmentRepository;
import com.leverx.repository.ProjectPositionRepository;
import com.leverx.repository.EmployeeRepository;
import com.leverx.utils.TransformDate;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Component
@AllArgsConstructor
public class WriteExcelReport {

  private final EmployeeRepository employeeRepository;
  private final ProjectPositionRepository projectPositionRepository;
  private final DepartmentRepository departmentRepository;

  public void writeToXmlFileStatisticOfUsersForMonth() {
    Map<String, Object[]> data = new TreeMap<>();
    data.put("1", new Object[] {"ID", "User Name", "Department", "Project", "JobTitle"});

    List<Employee> all = employeeRepository.findAllAsc();
    int column = 1;
    for (Employee employee : all) {
      Department department = findDepartment(employee);

      List<ProjectPosition> projectPositions = findProjectPosition(employee);
      for (ProjectPosition projectPosition: projectPositions) {
        Project project = projectPosition.getProject();
        String projectTitle = project.getTitle();
        column++;
          data.put(Integer.toString(column), new Object[] {employee.getId().toString(), employee.getFirstName() + employee.getLastName(), department.getTitle(), projectTitle, employee.getJobTitle()});
        }
      }
    writeDataToExcelFile("statistics.xlsx", data);
  }

  public void writeToXmlFileAvailableEmployeesDuringMonth() throws ParseException {
    Map<String, Object[]> data = new TreeMap<>();
    data.put("1", new Object[] {"ID", "User Name", "Department", "Project", "Start Date",  "End Date"});

    LocalDate date = TransformDate.addPeriodToLocalDate(30);
    List<Long> availableUsersId = projectPositionRepository.findAvailableUser(date);

    List<Employee> all = new ArrayList<>();
    for (Long userId : availableUsersId) {
      Employee employee = employeeRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
      "User with id = " + userId + " doesn't exists"));
      all.add(employee);
    }

    int count = 1;
    for (Employee employee : all) {
      count++;
      Department department = findDepartment(employee);
      List<ProjectPosition> projectPositions = findProjectPosition(employee);

      for (ProjectPosition projectPosition: projectPositions) {
        LocalDate endDate = projectPosition.getPositionEndDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endProjectDate = endDate.format(formatter);

        LocalDate startDate = projectPosition.getPositionStartDate();
        String startProjectDate = startDate.format(formatter);

        if(endDate.isBefore(LocalDate.now())){
          endProjectDate = "no active project";
          startProjectDate ="no active project";
        }
        Project project = projectPosition.getProject();
        data.put(Integer.toString(count), new Object[] {employee.getId().toString(), employee.getFirstName() + employee.getLastName(), department.getTitle(), project.getTitle(), startProjectDate, endProjectDate});
      }
    }
    writeDataToExcelFile("statisticsForAvailableEmployee.xlsx", data);
  }

  public List<ProjectPosition> findProjectPosition(Employee employee){
    List<Long> allProjectPositionId = projectPositionRepository.findProjectPositionIdByUserId(employee.getId());
    List<ProjectPosition> projectPositions = new ArrayList<>();
    for (Long projectPositionId: allProjectPositionId) {
      ProjectPosition projectPosition = projectPositionRepository.findById(projectPositionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "Project position with id = " + projectPositionId + " doesn't exists"));
      projectPositions.add(projectPosition);
    }
    return projectPositions;
  }

  public Department findDepartment(Employee employee){
    return departmentRepository.findById(employee.getDepartment().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Department with id = " + employee.getDepartment().getId() + " doesn't exists"));
  }

  public void writeDataToExcelFile(String fileName, Map<String, Object[]> data){
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("User statistic");

    Set<String> keyset = data.keySet();
    int rowNumber = 0;
    for (String key : keyset) {
      Row row = sheet.createRow(rowNumber++);
      Object[] objArr = data.get(key);
      int columnNumber = 0;
      for (Object obj : objArr) {
        Cell cell = row.createCell(columnNumber++);
        if (obj instanceof String) cell.setCellValue((String) obj);
        else if (obj instanceof Integer) cell.setCellValue((Integer) obj);
      }
    }
    try {
      // Write the workbook in file system
      //C:\Program Files\Apache Software Foundation\Tomcat 8.5\bin
      FileOutputStream out = new FileOutputStream(fileName);
      workbook.write(out);
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
