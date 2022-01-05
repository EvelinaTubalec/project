package com.leverx.repositories;

import com.leverx.model.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProjectRepository extends JpaRepository<UserProject, Long> {

    UserProject findUserProjectById(Long userProjectId);
}
