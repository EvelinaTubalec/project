package com.leverx.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "users")
@EqualsAndHashCode(exclude = {"departments", "userProjects"})
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column private String email;

  @Column private String password;

  @Column private String position;

  @ManyToOne
  @JoinColumn(name = "department_id")
  private Department department;

  @OneToMany(mappedBy = "user")
  Set<UserProject> userProjects;
}
