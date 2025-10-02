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
        //TODO 제네릭으로 공통으로 뻬기
        CriteriaBuilder cb          = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq      = cb.createQuery(Long.class);
        Root<Member> root           = cq.from(Member.class);
        //
        
        cq                               = cq.select((cb.count(root)));
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
                //searchConditions.add(cb.like(root.get("memberId"), "%" + searchWord + "%"));
            } else if ("phoneNumber".equals(searchType)) {
                searchConditions.add(cb.equal(root.get("phoneNumber"), searchWord));
            }
        }

        cq = cq.where(cb.and(searchConditions.toArray(new Predicate[0])));

        return  em.createQuery(cq).getSingleResult();
    }

    public List<Member> findMembers(MemberListRequest memberListRequest) {
        CriteriaBuilder cb          = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq    = cb.createQuery(Member.class);
        Root<Member> root           = cq.from(Member.class);
        cq                          = cq.select(root);

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

            // 3-1. 아이디 검색 (LIKE 검색)
            if ("memberId".equals(searchType)) {
                searchConditions.add(cb.like(root.get("memberId"), "%" + searchWord + "%"));
                //searchConditions.add(cb.equal(root.get("memberId"), searchWord));
                // 3-2. 핸드폰번호 검색 (EQUAL 검색)
            } else if ("phoneNumber".equals(searchType)) {
                // 핸드폰번호는 보통 정확히 일치(Equal)를 사용합니다.
                searchConditions.add(cb.equal(root.get("phoneNumber"), searchWord));

                // 3-3. '회원명' 등 기본 검색 조건이 있을 경우 추가 (옵션)
                // } else if ("memberName".equals(searchType)) {
                //    searchConditions.add(cb.like(root.get("memberName"), "%" + searchWord + "%"));
            }
        }

        cq = cq.where(cb.and(searchConditions.toArray(new Predicate[0])));

        TypedQuery<Member> typedQuery = em.createQuery(cq);
        typedQuery.setFirstResult(memberListRequest.getPage());
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
