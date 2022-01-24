package com.leverx.repository;

import com.leverx.model.ProjectPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProjectPositionRepository extends JpaRepository<ProjectPosition, Long> {

  @Query(
      value =
          "SELECT DISTINCT employee_id "
              + "FROM project_position "
              + "WHERE position_start_date > :currentDate "
              + "OR position_end_date < :currentDate",
      nativeQuery = true)
  List<Long> findAvailableEmployee(@Param("currentDate") LocalDate currentDate);

  @Query(
      value =
          "SELECT MAX (project_position.position_start_date)\n"
              + "FROM project_position\n"
              + "WHERE employee_id = :employeeId "
              + "AND project_position.position_start_date > :currentDate",
      nativeQuery = true)
  LocalDate findAvailableDateOfEmployee(@Param("employeeId") Long userId, @Param("currentDate") LocalDate currentDate);

  @Query(
          value =
                  "SELECT MAX (project_position.position_end_date)\n"
                          + "FROM project_position\n"
                          + "WHERE employee_id = :employeeId "
                          + "AND project_position.position_end_date < :currentDate",
          nativeQuery = true)
  LocalDate findLastProjectPositionDateOfEmployee(@Param("employeeId") Long userId , @Param("currentDate") LocalDate currentDate);

  @Query(
          value =
                  "SELECT id "
                          + "FROM project_position "
                          + "WHERE employee_id = :employeeId "
                          + "AND (project_position.position_start_date >=:startDate "
                          + "OR position_end_date <:endDate) ",
          nativeQuery = true)
  List<Long> findProjectPositionIdByEmployeeIdDuringMonth(@Param("employeeId") Long employeeId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate) ;

  @Query(
          value =
                  "SELECT id "
                          + "FROM project_position "
                          + "WHERE employee_id = :employeeId "
                          + "AND (project_position.position_start_date >=:currentDate) ",
          nativeQuery = true)
  List<Long> findProjectPositionIdByEmployeeId(@Param("employeeId") Long employeeId, @Param("currentDate") LocalDate currentDate) ;

}
