package com.leverx.service;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.leverx.model.Employee;
import com.leverx.model.convertor.EmployeeConvertor;
import com.leverx.model.dto.response.EmployeeResponseDto;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoadingEmployeesFromCSVFileService {

  public List<Employee> findAllFromCSVFile() throws FileNotFoundException {
    CsvToBean csv = new CsvToBean();
    String csvFilename = "C:\\Users\\evelina.tubalets\\Desktop\\data.csv";
    CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
    // Set column mapping strategy
    List<Employee> employees = new ArrayList<>();
    List list = csv.parse(setColumMapping(), csvReader);
    for (Object object : list) {
      Employee employee = (Employee) object;
      employees.add(employee);
    }
    return employees;
  }

  private static ColumnPositionMappingStrategy setColumMapping()
  {
    ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
    strategy.setType(Employee.class);
    String[] columns = new String[] {"id", "firstName", "lastName", "email", "password", "jobTitle"};
    strategy.setColumnMapping(columns);
    return strategy;
  }
}
