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
          "select distinct employee_id "
              + "from project_position "
              + "where position_start_date > :currentDate "
              + "or position_end_date < :currentDate",
      nativeQuery = true)
  List<Long> findAvailableEmployee(@Param("currentDate") LocalDate currentDate);

  @Query(
      value =
          "select max (project_position.position_start_date)\n"
              + "from project_position\n"
              + "where employee_id = :employeeId "
              + "and project_position.position_start_date > :currentDate",
      nativeQuery = true)
  LocalDate findAvailableDateOfEmployee(@Param("employeeId") Long userId, @Param("currentDate") LocalDate currentDate);

  @Query(
          value =
                  "select max (project_position.position_end_date)\n"
                          + "from project_position\n"
                          + "where employee_id = :employeeId and " +
                          "project_position.position_end_date < :currentDate",
          nativeQuery = true)
  LocalDate findLastProjectPositionDateOfEmployee(@Param("employeeId") Long userId , @Param("currentDate") LocalDate currentDate);

  @Query(
          value =
                  "SELECT id "
                          + "from project_position "
                          + "where employee_id = :employeeId and (project_position.position_start_date >= '2022-01-01' " +
                          "or position_end_date < '2022-01-31') ",
          nativeQuery = true)
  List<Long> findProjectPositionIdByEmployeeId(@Param("employeeId") Long employeeId) ;
}
