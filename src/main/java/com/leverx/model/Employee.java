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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Builder
@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column private String email;

  @Column private String password;

  @Column private String jobTitle;

  @ManyToOne
  @JoinColumn(name = "department_id")
  private Department department;

  @OneToMany(mappedBy = "employee")
  Set<ProjectPosition> projectPositions;

  public Employee(String firstName, String lastName, String email, String password, String jobTitle, Department department) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.jobTitle = jobTitle;
    this.department = department;
  }

  public Employee(Long id, String firstName, String lastName, String email, String password, String jobTitle) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.jobTitle = jobTitle;
  }

  public Employee(Long id, String firstName, String lastName, String email, String password, String jobTitle, Department department) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.jobTitle = jobTitle;
    this.department = department;
  }
}
