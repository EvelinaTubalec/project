package com.leverx.repository;

import com.leverx.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query(value = "SELECT id FROM report WHERE file_name=:fileName", nativeQuery = true)
    Long findReportByReportName(@Param("fileName") String fileName);

    @Query(value = "SELECT id FROM report WHERE create_date IN (SELECT MAX(create_date) FROM report)", nativeQuery = true)
    Long findLastReport();

    @Query(value = "SELECT id FROM report WHERE create_date IN (SELECT MAX(create_date) FROM report WHERE type=:type)", nativeQuery = true)
    Long findLastIdReportByType(@Param("type") String type);
}
