package com.leverx.controller;

import com.leverx.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

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
}
