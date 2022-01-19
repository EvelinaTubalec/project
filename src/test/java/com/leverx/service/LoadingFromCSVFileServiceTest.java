package com.leverx.service;

import com.leverx.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
class LoadingFromCSVFileServiceTest {

    @InjectMocks
    LoadingFromCSVFileService loadingFromCSVFileService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllFromCSVFile() throws FileNotFoundException {
        Employee employee = new Employee(1L, "Lokesh","Gupta","email","password","ui" );
        Employee employee2 = new Employee(2L, "David","Miller","email","password","developer" );

        List<Employee> exceptedEmployees = new ArrayList<>();
        exceptedEmployees.add(employee);
        exceptedEmployees.add(employee2);
        List<Employee> actualEmployees = loadingFromCSVFileService.findAllFromCSVFile();

        Assertions.assertEquals(exceptedEmployees.size(), actualEmployees.size());
    }
}