package com.fnbadmin.controller.repository;

import com.fnbadmin.controller.request.CouponRequest;
import com.fnbadmin.controller.request.MemberRequest;
import com.fnbadmin.controller.request.OrderRequest;
import com.fnbadmin.domain.Coupon;
import com.fnbadmin.domain.CouponProduct;
import com.fnbadmin.domain.Member;
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
public class CouponRepository {

    private final EntityManager em;

    public CouponRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public int insertCoupon(Coupon coupon) {
        this.em.persist(coupon);
        return coupon.getId();
    }

    //TODO 벌크여부 파악하기
    public int insertCouponProducts(List<CouponProduct> couponProducts) {
        for (CouponProduct cp : couponProducts) {
            this.em.persist(cp);
        }

        return couponProducts.size();
    }

    public Coupon findCoupon(int couponId) {
        return this.em.createQuery("select c from Coupon c where c.id = :couponId", Coupon.class)
                .setParameter("couponId", couponId)
                .getSingleResult();
    }

    public List<CouponProduct> findCouponProducts(int couponId) {
        return this.em.createQuery("select cp from CouponProduct cp where cp.couponId = :couponId", CouponProduct.class)
                .setParameter("couponId", couponId)
                .getResultList();
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

        cq = cq.where(cb.and(this.buildConditions(couponRequest, cb, root).toArray(new Predicate[0])));

        TypedQuery<Coupon> typedQuery = em.createQuery(cq);
        typedQuery.setFirstResult(couponRequest.getPage() - 1);
        typedQuery.setMaxResults(couponRequest.getPageLimit());

        return typedQuery.getResultList();
    }

    public List<CouponProduct> findCouponProducts(List<Integer> couponIds) {
        return this.em.createQuery("select cp from CouponProduct cp where cp.couponId in (:couponIds)", CouponProduct.class)
                .setParameter("couponIds", couponIds)
                .getResultList();
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
