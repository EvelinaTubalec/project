package com.leverx.report;

import com.leverx.model.Report;
import com.leverx.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;

import static com.leverx.report.WriteExcelReport.EMPLOYEES_MONTH_STATISTIC_FILE;

@Component
@AllArgsConstructor
public class Schedule {

    private final WriteExcelReport writeExcelReport;

    private final ReportService reportService;

    @Scheduled(cron = "0 8 1 * * *")
    public void reportGenerating() throws ParseException {
        String reportName = writeExcelReport.writeToXmlFileStatisticOfUsersForMonth();
        Report report = new Report(new Timestamp(System.currentTimeMillis()), reportName, EMPLOYEES_MONTH_STATISTIC_FILE);
        reportService.save(report);
        writeExcelReport.writeToXmlFileAvailableEmployeesDuringMonth();
    }
}