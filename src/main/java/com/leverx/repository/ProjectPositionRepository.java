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
          "select distinct user_id "
              + "from project_position "
              + "where position_start_date > :currentDate "
              + "or position_end_date < :currentDate",
      nativeQuery = true)
  List<Long> findAvailableUser(@Param("currentDate") LocalDate currentDate);

  @Query(
      value =
          "select max (project_position.position_start_date)\n"
              + "from project_position\n"
              + "where user_id = :userId "
              + "and project_position.position_start_date > :currentDate",
      nativeQuery = true)
  LocalDate findAvailableDateOfUser(@Param("userId") Long userId, @Param("currentDate") LocalDate currentDate);

  @Query(
          value =
                  "select id "
                          + "from project_position "
                          + "where user_id = :userId and project_position.position_start_date >= '2022-01-01' ",
          nativeQuery = true)
  List<Long> findProjectPositionIdByUserId(@Param("userId") Long userId) ;
}
