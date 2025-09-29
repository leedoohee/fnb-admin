package com.fnbadmin.controller.service;

import com.fnbadmin.controller.repository.ManageMemberRepository;
import org.springframework.stereotype.Service;

@Service
public class ManageMemberService {

    private final ManageMemberRepository manageMemberRepository;

    public ManageMemberService(ManageMemberRepository manageMemberRepository) {
        this.manageMemberRepository = manageMemberRepository;
    }


}
