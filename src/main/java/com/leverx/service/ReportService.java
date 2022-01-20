package com.leverx.service;

import com.leverx.model.Department;
import com.leverx.model.Employee;
import com.leverx.model.Project;
import com.leverx.model.ProjectPosition;
import com.leverx.model.Report;
import com.leverx.repository.DepartmentRepository;
import com.leverx.repository.EmployeeRepository;
import com.leverx.repository.ProjectPositionRepository;
import com.leverx.repository.ReportRepository;
import com.leverx.utils.TransformDate;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

@Service
@AllArgsConstructor
public class ReportService {

    public static final String ID = "ID";
    public static final String USER_NAME = "User Name";
    public static final String DEPARTMENT = "Department";
    public static final String PROJECT = "Project";
    public static final String START_DATE = "Start Date";
    public static final String END_DATE = "End Date";
    public static final String JOB_TITLE = "JobTitle";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String PATH_TO_FILE =
            "C:" + File.separator
                    + "Users" + File.separator
                    + "evelina.tubalets" + File.separator
                    + "Projects" + File.separator
                    + "reports" + File.separator;
    public static final String REPORT_OF_AVAILABLE_EMPLOYEES_FILE = "available_employees.xlsx";
    public static final String EMPLOYEES_MONTH_STATISTIC_FILE = "employees_month_statistic.xlsx";
    public static String nameOfReport;
    private final EmployeeRepository employeeRepository;
    private final ProjectPositionRepository projectPositionRepository;
    private final DepartmentRepository departmentRepository;
    private final ReportRepository reportRepository;

    public Report save(Report report) {
        return reportRepository.save(report);
    }

    public String getStatisticForMonth() {
        String reportName = writeReportOfEmployeesMonthStatisticToXmlFile();
        Report report = new Report(new Timestamp(System.currentTimeMillis()), reportName, EMPLOYEES_MONTH_STATISTIC_FILE );
        save(report);
        return reportName;
    }

    public String replaceReportOfAvailableEmployees() throws ParseException {
        String reportName = writeAvailableEmployeesDuringMonthReportToXmlFile();
        if(reportRepository.findReportByReportName(reportName) == null){
            Report report = new Report(new Timestamp(System.currentTimeMillis()), reportName, REPORT_OF_AVAILABLE_EMPLOYEES_FILE);
            save(report);
        }else{
        Long id = reportRepository.findReportByReportName(reportName);
            Report reportById = reportRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Report doesn't exists"));
            reportById.setCreateDate(new Timestamp(System.currentTimeMillis()));
        save(reportById);
        }
        return reportName;
    }

    public String getLastReport() {
        Long lastReport = reportRepository.findLastReport();
        Report report = reportRepository.findById(lastReport).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Report doesn't exists"));
        return report.getFileName();
    }

    public String writeReportOfEmployeesMonthStatisticToXmlFile() {
        Map<String, Object[]> data = new TreeMap<>();
        data.put("1", new Object[] {ID, USER_NAME, DEPARTMENT, PROJECT, JOB_TITLE, START_DATE, END_DATE});

        List<Employee> all = employeeRepository.findAllAsc();
        int column = 1;
        for (Employee employee : all) {
            Department department = findDepartment(employee);

            List<ProjectPosition> projectPositions = findProjectPosition(employee);
            for (ProjectPosition projectPosition: projectPositions) {
                Project project = projectPosition.getProject();
                String projectTitle = project.getTitle();
                column++;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
                LocalDate endDate = projectPosition.getPositionEndDate();
                String endProjectDate = endDate.format(formatter);

                LocalDate startDate = projectPosition.getPositionStartDate();
                String startProjectDate = startDate.format(formatter);
                data.put(Integer.toString(column), new Object[] {
                        employee.getId().toString(),
                        employee.getFirstName() + employee.getLastName(),
                        department.getTitle(),
                        projectTitle,
                        employee.getJobTitle(),
                        startProjectDate,
                        endProjectDate});
            }
        }
        return writeDataToExcelFile(EMPLOYEES_MONTH_STATISTIC_FILE, data);
    }

    public String writeAvailableEmployeesDuringMonthReportToXmlFile() throws ParseException {
        Map<String, Object[]> data = new TreeMap<>();
        data.put("1", new Object[] {ID, USER_NAME, DEPARTMENT, PROJECT, START_DATE, END_DATE});

        LocalDate date = TransformDate.addPeriodToLocalDate(30);
        List<Long> availableUsersId = projectPositionRepository.findAvailableEmployee(date);

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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
                LocalDate endDate = projectPosition.getPositionEndDate();
                String endProjectDate = endDate.format(formatter);

                LocalDate startDate = projectPosition.getPositionStartDate();
                String startProjectDate = startDate.format(formatter);

                if(endDate.isBefore(LocalDate.now())){
                    endProjectDate = "no active project";
                    startProjectDate ="no active project";
                }
                Project project = projectPosition.getProject();
                data.put(Integer.toString(count), new Object[] {
                        employee.getId().toString(),
                        employee.getFirstName() + employee.getLastName(),
                        department.getTitle(),
                        project.getTitle(),
                        startProjectDate,
                        endProjectDate});
            }
        }
        return writeDataToExcelFile(REPORT_OF_AVAILABLE_EMPLOYEES_FILE, data);
    }

    public String writeDataToExcelFile(String fileName, Map<String, Object[]> data){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Employee statistic");

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
            if(Objects.equals(fileName, REPORT_OF_AVAILABLE_EMPLOYEES_FILE)){
                nameOfReport = PATH_TO_FILE  + fileName;
            }else{
                if(reportRepository.findLastIdReportByType(EMPLOYEES_MONTH_STATISTIC_FILE) == null){
                    nameOfReport = PATH_TO_FILE + 1 + "_" + fileName;
                } else {
                    Long lastReport = reportRepository.findLastIdReportByType(EMPLOYEES_MONTH_STATISTIC_FILE);
                    String s = lastReport.toString();
                    nameOfReport = PATH_TO_FILE + s + "_" + fileName;
                }
            }
            File file = new File(nameOfReport);
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nameOfReport;
    }

    public List<ProjectPosition> findProjectPosition(Employee employee){
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        List<Long> allProjectPositionId = projectPositionRepository.findProjectPositionIdByEmployeeIdDuringMonth(employee.getId(), startDate, endDate);
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
}
