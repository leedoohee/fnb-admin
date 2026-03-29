package com.fnbadmin.controller.repository;

import com.fnbadmin.controller.request.CouponRequest;
import com.fnbadmin.controller.request.MemberRequest;
import com.fnbadmin.controller.request.OrderRequest;
import com.fnbadmin.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CouponRepository {

    private final EntityManager em;

    public CouponRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public int insertCoupon(Coupon coupon) {
        this.em.persist(coupon);
        return coupon.getCouponId();
    }

    //TODO 벌크여부 파악하기
    public int insertCouponProducts(List<CouponProduct> couponProducts) {
        for (CouponProduct cp : couponProducts) {
            this.em.persist(cp);
        }

        return couponProducts.size();
    }

    public Coupon findCoupon(int couponId) {

        CriteriaBuilder cb         = em.getCriteriaBuilder();
        CriteriaQuery<Coupon> cq   = cb.createQuery(Coupon.class);
        Root<Coupon> root          = cq.from(Coupon.class);

        root.fetch("couponProduct", JoinType.LEFT);

        cq = cq.select(root)
                .where(cb.and(cb.equal(root.get("id"), couponId)))
                .distinct(true);

        TypedQuery<Coupon> typedQuery = em.createQuery(cq);
        typedQuery.setMaxResults(1);

        return !typedQuery.getResultList().isEmpty() ? typedQuery.getResultList().get(0) : null;
    }

    public Long getTotalCouponCount(CouponRequest couponRequest) {
        CriteriaBuilder cb          = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq      = cb.createQuery(Long.class);
        Root<Coupon> root           = cq.from(Coupon.class);

        cq = cq.where(cb.and(this.buildConditions(couponRequest, cb, root).toArray(new Predicate[0])));
        cq = cq.select((cb.count(root)));

        return  em.createQuery(cq).getSingleResult();
    }

    public List<Coupon> findCoupons(CouponRequest couponRequest) {
        CriteriaBuilder cb         = em.getCriteriaBuilder();
        CriteriaQuery<Coupon> cq   = cb.createQuery(Coupon.class);
        Root<Coupon> root          = cq.from(Coupon.class);

        cq = cq.select(root)
                .where(cb.and(this.buildConditions(couponRequest, cb, root).toArray(new Predicate[0])))
                .distinct(true);

        TypedQuery<Coupon> typedQuery = em.createQuery(cq);
        typedQuery.setFirstResult(couponRequest.getPage() - 1);
        typedQuery.setMaxResults(couponRequest.getPageLimit());

        return typedQuery.getResultList();
    }

    public List<CouponProduct> findCouponProducts(List<Integer> couponIds) {
        CriteriaBuilder cb                  = em.getCriteriaBuilder();
        CriteriaQuery<CouponProduct> cq     = cb.createQuery(CouponProduct.class);
        Root<CouponProduct> root            = cq.from(CouponProduct.class);

        cq = cq.where(cb.and(root.get("couponId").in(couponIds)));

        TypedQuery<CouponProduct> typedQuery = em.createQuery(cq);

        return typedQuery.getResultList();
    }

    private List<Predicate> buildConditions(CouponRequest couponRequest, CriteriaBuilder cb, Root<Coupon> root) {
        List<Predicate> searchConditions    = new ArrayList<>();
        String searchType                   = couponRequest.getSearchType();
        String searchWord                   = couponRequest.getSearchWord();

        if(couponRequest.getApplyStartDate() != null && couponRequest.getApplyEndDate() != null){
            searchConditions.add(cb.between(root.get("applyStartAt"), couponRequest.getApplyStartDate(), couponRequest.getApplyEndDate()));
        }

        if(couponRequest.getStatus() != null && !couponRequest.getStatus().isEmpty()){
            searchConditions.add(cb.and(root.get("status").in(couponRequest.getStatus())));
        }

        if (searchWord != null && !searchWord.trim().isEmpty()) {
            if ("couponName".equals(searchType)) {
                searchConditions.add(cb.like(root.get("name"), "%" + searchWord + "%"));
            } else if ("couponId".equals(searchType)) {
                searchConditions.add(cb.equal(root.get("id"), searchWord));
            }
        }

        return  searchConditions;
    }
}
