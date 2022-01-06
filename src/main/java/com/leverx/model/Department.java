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
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "departments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column private String title;

  @OneToMany(mappedBy = "department", cascade = ALL, fetch = EAGER, orphanRemoval = true)
  private Set<User> users;
}
