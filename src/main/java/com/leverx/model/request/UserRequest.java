package com.leverx.model.request;

import com.leverx.model.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder
@AllArgsConstructor
public class UserRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String position;

    private Long departmentId;
}
