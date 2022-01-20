package com.leverx.service;

import com.leverx.model.Report;
import com.leverx.report.WriteExcelReport;
import com.leverx.repository.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.text.ParseException;

import static com.leverx.report.WriteExcelReport.EMPLOYEES_MONTH_STATISTIC_FILE;
import static com.leverx.report.WriteExcelReport.REPORT_OF_AVAILABLE_EMPLOYEES_FILE;

@Service
@AllArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final WriteExcelReport writeExcelReport;

    public Report save(Report report) {
        return reportRepository.save(report);
    }

    public String getStatisticForMonth() {
        String reportName = writeExcelReport.writeToXmlFileStatisticOfUsersForMonth();
        Report report = new Report(new Timestamp(System.currentTimeMillis()), reportName, EMPLOYEES_MONTH_STATISTIC_FILE );
        save(report);
        return reportName;
    }

    public String replaceReportOfAvailableEmployees() throws ParseException {
        String reportName = writeExcelReport.writeToXmlFileAvailableEmployeesDuringMonth();
        if(reportRepository.findReportByReportName(reportName) == null){
            Report report = new Report(new Timestamp(System.currentTimeMillis()), reportName, REPORT_OF_AVAILABLE_EMPLOYEES_FILE);
            save(report);
        }else{
        Long id = (reportRepository.findReportByReportName(reportName));
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
}
