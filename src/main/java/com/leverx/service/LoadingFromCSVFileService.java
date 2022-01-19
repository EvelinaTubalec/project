package com.leverx.service;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.leverx.model.Employee;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class LoadingFromCSVFileService {

  public static final String ID = "id";
  public static final String FIRST_NAME = "firstName";
  public static final String LAST_NAME = "lastName";
  public static final String EMAIL = "email";
  public static final String PASSWORD = "password";
  public static final String JOB_TITLE = "jobTitle";
  public static final String FILE_NAME = "C:\\Users\\evelina.tubalets\\Projects\\src\\main\\resources\\data.csv";

  public List<Employee> findAllFromCSVFile() throws FileNotFoundException {
    CsvToBean<Employee> csv = new CsvToBean<>();
    CSVReader csvReader = new CSVReader(new FileReader(FILE_NAME));
    return csv.parse(setColumMapping(), csvReader);
  }

  private static ColumnPositionMappingStrategy<Employee> setColumMapping()
  {
    ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
    strategy.setType(Employee.class);
    String[] columns = new String[] {ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, JOB_TITLE};
    strategy.setColumnMapping(columns);
    return strategy;
  }
}
