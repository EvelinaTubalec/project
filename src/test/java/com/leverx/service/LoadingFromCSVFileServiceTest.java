package com.leverx.service;

import com.leverx.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class LoadingFromCSVFileServiceTest {

    @InjectMocks
    private LoadingFromCSVFileService loadingFromCSVFileService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllFromCSVFile_thenReturnAllEmployees() throws FileNotFoundException {
        Employee emp1 = new Employee(1L, "Lokesh","Gupta","email","password","ui" );
        Employee emp2 = new Employee(2L, "David","Miller","email","password","developer" );

        List<Employee> exceptedEmployees = new ArrayList<>();
        exceptedEmployees.add(emp1);
        exceptedEmployees.add(emp2);

        List<Employee> actualEmployees = loadingFromCSVFileService.findAllFromCSVFile();

        assertEquals(exceptedEmployees.size(), actualEmployees.size());

        Employee expectedEmp = exceptedEmployees.get(1);
        Employee actualEmp = actualEmployees.get(1);

        assertEquals(expectedEmp.getFirstName(), actualEmp.getFirstName());
        assertEquals(expectedEmp.getLastName(), actualEmp.getLastName());
        assertEquals(expectedEmp.getEmail(), actualEmp.getEmail());
        assertEquals(expectedEmp.getPassword(), actualEmp.getPassword());
        assertEquals(expectedEmp.getJobTitle(), actualEmp.getJobTitle());
    }
}