package com.leverx.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Builder
@Entity
@Table(name = "project_position")
@AllArgsConstructor
@NoArgsConstructor
public class ProjectPosition {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "position_start_date")
  private LocalDate positionStartDate;

  @Column(name = "position_end_date")
  private LocalDate positionEndDate;

  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;

  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;

}
