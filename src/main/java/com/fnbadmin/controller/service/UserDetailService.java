package com.fnbadmin.controller.service;

import com.fnbadmin.controller.repository.ManageMemberRepository;
import com.fnbadmin.domain.ManageMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final ManageMemberRepository manageMemberRepository;

    @Override
    public ManageMember loadUserByUsername(String userId) {
        return this.manageMemberRepository.findManageMember(userId);
    }
}