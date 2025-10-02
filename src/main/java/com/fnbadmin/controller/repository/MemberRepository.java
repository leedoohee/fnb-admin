package com.fnbadmin.controller.repository;

import com.fnbadmin.controller.request.MemberRequest;
import com.fnbadmin.domain.Member;
import com.fnbadmin.domain.MemberCoupon;
import com.fnbadmin.domain.MemberGrade;
import com.fnbadmin.domain.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemberRepository {

    private final EntityManager em;

    public MemberRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Long getTotalMemberCount(MemberRequest memberRequest) {
        //TODO 제네릭으로 공통으로 뻬기
        CriteriaBuilder cb          = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq      = cb.createQuery(Long.class);
        Root<Member> root           = cq.from(Member.class);

        cq = cq.where(cb.and(this.buildConditions(memberRequest, cb, root).toArray(new Predicate[0])));
        cq = cq.select((cb.count(root)));

        return  em.createQuery(cq).getSingleResult();
    }

    public List<Member> findMembers(MemberRequest memberRequest) {
        CriteriaBuilder cb          = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq    = cb.createQuery(Member.class);
        Root<Member> root           = cq.from(Member.class);

        cq = cq.where(cb.and(this.buildConditions(memberRequest, cb, root).toArray(new Predicate[0])));

        TypedQuery<Member> typedQuery = em.createQuery(cq);
        typedQuery.setFirstResult(memberRequest.getPage() - 1);
        typedQuery.setMaxResults(memberRequest.getPageLimit());

        return typedQuery.getResultList();
    }

    public Member findMemberById(String memberId) {
        CriteriaBuilder cb          = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq    = cb.createQuery(Member.class);
        Root<Member> root           = cq.from(Member.class);

        cq = cq.where(cb.and(cb.equal(root.get("memberId"), memberId)));
        TypedQuery<Member> typedQuery = em.createQuery(cq);

        return typedQuery.getSingleResult();
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

    private List<Predicate> buildConditions(MemberRequest memberRequest, CriteriaBuilder cb, Root<Member> root) {
        List<Predicate> searchConditions    = new ArrayList<>();
        String searchType                   = memberRequest.getSearchType();
        String searchWord                   = memberRequest.getSearchWord();

        if(memberRequest.getStartDate() != null && memberRequest.getEndDate() != null){
            searchConditions.add(cb.between(root.get("joinDate"), memberRequest.getStartDate(), memberRequest.getEndDate()));
        }

        if(memberRequest.getMemberGrade() != null && !memberRequest.getMemberGrade().isEmpty()){
            searchConditions.add(cb.equal(root.get("grade"), memberRequest.getMemberGrade()));
        }

        if (searchWord != null && !searchWord.trim().isEmpty()) {
            if ("memberId".equals(searchType)) {
                searchConditions.add(cb.like(root.get("memberId"), "%" + searchWord + "%"));
            } else if ("phoneNumber".equals(searchType)) {
                searchConditions.add(cb.equal(root.get("phoneNumber"), searchWord));
            }
        }

        return  searchConditions;
    }
}
