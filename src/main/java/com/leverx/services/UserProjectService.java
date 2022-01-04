package com.leverx.services;

import com.leverx.repositories.UserProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProjectService {

    private final UserProjectRepository userProjectRepository;


}
