package com.fnbadmin.controller.repository;

import com.fnbadmin.domain.Coupon;
import com.fnbadmin.domain.CouponProduct;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

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

    public List<Coupon> findCoupons(String startDate, String endDate, String status, int page, int pageLimit) {
        return this.em.createQuery("select c from Coupon c where c.createdAt >= :startDate and c.createdAt <= :endDate", Coupon.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<CouponProduct> findCouponProducts(String couponIds) {
        return this.em.createQuery("select cp from CouponProduct cp where cp.couponId in (:couponIds)", CouponProduct.class)
                .setParameter("couponIds", couponIds)
                .getResultList();
    }
}
