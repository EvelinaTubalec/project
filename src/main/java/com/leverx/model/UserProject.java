package com.leverx.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Builder
@Entity
@Table(name = "user_projects")
@AllArgsConstructor
@NoArgsConstructor
public class UserProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "position_start_date")
    private LocalDate positionStartDate;

    @Column(name = "position_end_date")
    private LocalDate positionEndDate;
}
