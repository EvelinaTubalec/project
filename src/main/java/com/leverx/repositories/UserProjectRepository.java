package com.leverx.repositories;

import com.leverx.model.UserProject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectRepository extends CrudRepository<UserProject, Long> {

    UserProject findUserProjectById(Long userProjectId);
}
