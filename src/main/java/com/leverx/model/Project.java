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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Builder
@Entity
@Table(name = "project")
@AllArgsConstructor
@NoArgsConstructor
public class Project {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column private String title;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @OneToMany(mappedBy = "project")
  Set<Position> userProjects;

  public Project(String title, LocalDate startDate, LocalDate endDate) {
    this.title = title;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public Project(Long id, String title, LocalDate startDate, LocalDate endDate) {
    this.id = id;
    this.title = title;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
