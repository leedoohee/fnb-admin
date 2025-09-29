package com.fnbadmin.controller.repository;

import com.fnbadmin.domain.ManageMember;
import com.fnbadmin.domain.MemberCoupon;
import com.fnbadmin.domain.MemberGrade;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManageMemberRepository {

    private final EntityManager em;

    public ManageMemberRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public ManageMember findManageMember(String manageMemberId) {
        return this.em.createQuery("select a from ManageMember a where a.manageMemberId = :manageMemberId", ManageMember.class)
                .setParameter("manageMemberId", manageMemberId)
                .getSingleResult();
    }
}
