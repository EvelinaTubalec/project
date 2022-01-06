package com.leverx.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "projects")
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
  Set<UserProject> userProjects;

}
