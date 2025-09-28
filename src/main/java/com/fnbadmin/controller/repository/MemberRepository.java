package com.fnbadmin.controller.repository;

import com.fnbadmin.domain.Member;
import com.fnbadmin.domain.MemberCoupon;
import com.fnbadmin.domain.MemberGrade;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberRepository {

    private final EntityManager em;

    public MemberRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Member findMemberById(String memberId) {
        return this.em.createQuery("select m from Member m where m.id = :memberId", Member.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }

    public List<MemberCoupon> findMemberCoupons(int memberId) {
        return this.em.createQuery("select mc from MemberCoupon mc where mc.memberId = :memberId", MemberCoupon.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<MemberGrade> findAllMemberGrades() {
        return this.em.createQuery("select mg from MemberGrade mg", MemberGrade.class)
                .getResultList();
    }
}
