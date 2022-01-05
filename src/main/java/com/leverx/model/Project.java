package com.leverx.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@Entity
@Table(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"projects"})
public class Project {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "project")
    Set<UserProject> userProjects;
}
