package com.leverx.report;

import com.leverx.model.Department;
import com.leverx.model.Project;
import com.leverx.model.ProjectPosition;
import com.leverx.model.User;
import com.leverx.repository.DepartmentRepository;
import com.leverx.repository.ProjectPositionRepository;
import com.leverx.repository.ProjectRepository;
import com.leverx.repository.UserRepository;
import com.leverx.utils.TransformDate;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
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

  private final UserRepository userRepository;
  private final ProjectPositionRepository projectPositionRepository;
  private final ProjectRepository projectRepository;
  private final DepartmentRepository departmentRepository;

  public void writeToXmlFileStatisticOfUsers() {
    Map<String, Object[]> data = new TreeMap<>();
    data.put("1", new Object[] {"ID", "User Name", "Department", "Project", "JobTitle"});

    List<User> all = userRepository.findAllAsc();
    int column = 1;
    for (User user : all) {
      column++;
      Department department = findDepartment(user);
      Project project = findProject(user);
      ProjectPosition projectPosition = findProjectPosition(user);

      String projectTitle = project.getTitle();
      if(projectPosition.getPositionEndDate().isBefore(LocalDate.now())){
        projectTitle = "no active project";
      }
      data.put(Integer.toString(column), new Object[] {user.getId().toString(), user.getFirstName() + user.getLastName(), department.getTitle(), projectTitle, user.getJobTitle()});
    }
    writeDataToFile("statistics.xlsx", data);
  }

  public void writeToXmlFileAvailableEmployees() throws ParseException {
    Map<String, Object[]> data = new TreeMap<>();
    data.put("1", new Object[] {"ID", "User Name", "Department", "Project", "Start Date",  "End Date"});

    LocalDate date = TransformDate.addPeriodToLocalDate(30);
    List<Long> availableUsersId = projectPositionRepository.findAvailableUser(date);
    List<User> all = new ArrayList<>();
    for (Long userId : availableUsersId) {
      User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
      "User with id = " + userId + " doesn't exists"));
      all.add(user);
    }

    int count = 1;
    for (User user : all) {
      count++;
      Department department = findDepartment(user);
      Project project = findProject(user);
      ProjectPosition projectPosition = findProjectPosition(user);

      LocalDate endDate = projectPosition.getPositionEndDate();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      String endProjectDate = endDate.format(formatter);

      LocalDate startDate = projectPosition.getPositionStartDate();
      String startProjectDate = startDate.format(formatter);

      if(endDate.isBefore(LocalDate.now())){
        endProjectDate = "no active project";
        startProjectDate ="no active project";
        }
      data.put(Integer.toString(count), new Object[] {user.getId().toString(), user.getFirstName() + user.getLastName(), department.getTitle(), project.getTitle(), startProjectDate, endProjectDate});
    }
    writeDataToFile("statisticsForAvailableEmployee.xlsx", data);
  }

  public ProjectPosition findProjectPosition(User user){
    Long projectPositionId = projectPositionRepository.findProjectPositionIdByUserId(user.getId());
    return projectPositionRepository.findById(projectPositionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Project position with id = " + projectPositionId + " doesn't exists"));
  }

  public Project findProject(User user){
    Long projectId = projectPositionRepository.findProjectByUserId(user.getId());
    return projectRepository.findById(projectId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Project with id = " + projectId + " doesn't exists"));
  }

  public Department findDepartment(User user){
    return departmentRepository.findById(user.getDepartment().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Department with id = " + user.getDepartment().getId() + " doesn't exists"));
  }

  public void writeDataToFile(String fileName, Map<String, Object[]> data){
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("User statistic");

    Set<String> keyset = data.keySet();
    int rownum = 0;
    for (String key : keyset) {
      Row row = sheet.createRow(rownum++);
      Object[] objArr = data.get(key);
      int cellnum = 0;
      for (Object obj : objArr) {
        Cell cell = row.createCell(cellnum++);
        if (obj instanceof String) cell.setCellValue((String) obj);
        else if (obj instanceof Integer) cell.setCellValue((Integer) obj);
      }
    }
    try {
      // Write the workbook in file system
      //C:\Program Files\Apache Software Foundation\Tomcat 8.5\bin
      FileOutputStream out = new FileOutputStream(new File(fileName));
      workbook.write(out);
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
