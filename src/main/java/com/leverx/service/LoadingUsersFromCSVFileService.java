package com.leverx.service;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.leverx.model.User;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoadingUsersFromCSVFileService {

  public List<User> findAllFromCSVFile() throws FileNotFoundException {
    CsvToBean csv = new CsvToBean();
    String csvFilename = "C:\\Users\\evelina.tubalets\\Desktop\\data.csv";
    CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
    // Set column mapping strategy
    List<User> users = new ArrayList<>();
    List list = csv.parse(setColumMapping(), csvReader);
    for (Object object : list) {
      User user = (User) object;
      users.add(user);
    }
    return users;
  }

  private static ColumnPositionMappingStrategy setColumMapping() {
    ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
    strategy.setType(User.class);
    String[] columns = new String[] {"id", "firstName", "lastName", "email", "password", "jobTitle"};
    strategy.setColumnMapping(columns);
    return strategy;
  }
}
