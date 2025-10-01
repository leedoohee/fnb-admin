package com.fnbadmin.controller.repository;

import com.fnbadmin.domain.Member;
import com.fnbadmin.domain.MemberCoupon;
import com.fnbadmin.domain.MemberGrade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberRepository {

    private final EntityManager em;

    public MemberRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Long getTotalMemberCount() {
        Query query = this.em.createQuery("select count(m.id) from Member m", Long.class);
        return (Long) query.getSingleResult();
    }

    public List<Member> findMembers(int page, int pageLimit) {
        Query query = this.em.createQuery("select m from Member m", Member.class);
        query.setFirstResult(this.getPageCount(page, pageLimit));
        query.setMaxResults(pageLimit);

        return query.getResultList();
    }

    public Member findMemberById(String memberId) {
        return this.em.createQuery("select m from Member m where m.memberId = :memberId", Member.class)
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

    private int getPageCount(int page, int pageLimit) {
        return (page - 1) * pageLimit;
    }
}
