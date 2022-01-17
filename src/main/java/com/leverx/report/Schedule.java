package com.leverx.report;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
@AllArgsConstructor
public class Schedule {

    private final WriteExcelReport writeExcelReport;

    @Scheduled(cron = "0 8 1 * * *")
    public void reportGenerating() throws ParseException {
        writeExcelReport.writeToXmlFileStatisticOfUsersForMonth();
        writeExcelReport.writeToXmlFileAvailableEmployeesDuringMonth();
    }
}