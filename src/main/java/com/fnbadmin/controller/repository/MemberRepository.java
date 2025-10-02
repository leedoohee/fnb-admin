package com.fnbadmin.controller.repository;

import com.fnbadmin.controller.request.MemberListRequest;
import com.fnbadmin.domain.Member;
import com.fnbadmin.domain.MemberCoupon;
import com.fnbadmin.domain.MemberGrade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MemberRepository {

    private final EntityManager em;

    public MemberRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Long getTotalMemberCount(MemberListRequest memberListRequest) {
        List<Predicate> searchConditions = new ArrayList<>();
        //TODO 제네릭으로 공통으로 뻬기
        CriteriaBuilder cb          = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq      = cb.createQuery(Long.class);
        Root<Member> root           = cq.from(Member.class);
        //
        String searchType           = memberListRequest.getSearchType();
        String searchWord           = memberListRequest.getSearchWord();

        if(memberListRequest.getStartDate() != null && memberListRequest.getEndDate() != null){
            searchConditions.add(cb.between(root.get("joinDate"), memberListRequest.getStartDate(), memberListRequest.getEndDate()));
        }

        if(memberListRequest.getMemberGrade() != null && !memberListRequest.getMemberGrade().isEmpty()){
            searchConditions.add(cb.equal(root.get("grade"), memberListRequest.getMemberGrade()));
        }

        if (searchWord != null && !searchWord.trim().isEmpty()) {
            if ("memberId".equals(searchType)) {
                searchConditions.add(cb.equal(root.get("memberId"), searchWord));
                //searchConditions.add(cb.like(root.get("memberId"), "%" + searchWord + "%"));
            } else if ("phoneNumber".equals(searchType)) {
                searchConditions.add(cb.equal(root.get("phoneNumber"), searchWord));
            }
        }

        cq = cq.where(cb.and(searchConditions.toArray(new Predicate[0])));
        cq = cq.select((cb.count(root)));

        return  em.createQuery(cq).getSingleResult();
    }

    public List<Member> findMembers(MemberListRequest memberListRequest) {
        CriteriaBuilder cb          = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq    = cb.createQuery(Member.class);
        Root<Member> root           = cq.from(Member.class);

        List<Predicate> searchConditions = new ArrayList<>();
        String searchType = memberListRequest.getSearchType();
        String searchWord = memberListRequest.getSearchWord();

        if(memberListRequest.getStartDate() != null && memberListRequest.getEndDate() != null){
            searchConditions.add(cb.between(root.get("joinDate"), memberListRequest.getStartDate(), memberListRequest.getEndDate()));
        }

        if(memberListRequest.getMemberGrade() != null && !memberListRequest.getMemberGrade().isEmpty()){
            searchConditions.add(cb.equal(root.get("grade"), memberListRequest.getMemberGrade()));
        }

        if (searchWord != null && !searchWord.trim().isEmpty()) {
            if ("memberId".equals(searchType)) {
                searchConditions.add(cb.equal(root.get("memberId"), searchWord));
            } else if ("phoneNumber".equals(searchType)) {
                searchConditions.add(cb.equal(root.get("phoneNumber"), searchWord));
            }
        }

        cq = cq.where(cb.and(searchConditions.toArray(new Predicate[0])));

        TypedQuery<Member> typedQuery = em.createQuery(cq);
        typedQuery.setFirstResult(memberListRequest.getPage() - 1);
        typedQuery.setMaxResults(memberListRequest.getPageLimit());

        return typedQuery.getResultList();
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
