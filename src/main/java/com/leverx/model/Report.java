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
import javax.persistence.Table;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Builder
@Entity
@Table(name = "report")
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "type")
    private String type;

    public Report(Timestamp createDate, String fileName, String type) {
        this.createDate = createDate;
        this.fileName = fileName;
        this.type = type;
    }
}
