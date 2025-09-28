package com.fnbadmin.controller.service;

import com.fnbadmin.controller.repository.CouponRepository;
import com.fnbadmin.controller.request.CouponRequest;
import com.fnbadmin.controller.request.CreateCouponRequest;
import com.fnbadmin.controller.response.CouponInfoResponse;
import com.fnbadmin.controller.response.CouponListResponse;
import com.fnbadmin.domain.Coupon;
import com.fnbadmin.domain.CouponProduct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public List<CouponListResponse> getList(CouponRequest couponRequest) {
        List<CouponListResponse> responses = new ArrayList<>();

        List<Coupon> coupons = this.couponRepository.findCoupons(couponRequest.getApplyStartDate(),
                couponRequest.getApplyEndDate(), couponRequest.getStatus(),
                couponRequest.getPage(), couponRequest.getPageLimit());

        //List<String> couponIdList = coupons.stream().map(Coupon::getId).map(String::valueOf).toList();
        //String couponIds          = String.join(",", couponIdList);

        //List<CouponProduct> couponProducts = this.couponRepository.findCouponProducts(couponIds);

        /*for (Coupon coupon : coupons) {
            List<CouponProduct> relatedProducts = couponProducts.stream()
                    .filter(cp -> cp.getCouponId() == coupon.getId())
                    .collect(Collectors.toList());

            coupon.setCouponProducts(relatedProducts);
        }*/

        for (Coupon coupon : coupons) {
            responses.add(CouponListResponse.builder()
                    .couponId(coupon.getId())
                    .description(coupon.getDescription())
                    .applyStartDate(String.valueOf(coupon.getApplyStartAt()))
                    .applyEndDate(String.valueOf(coupon.getApplyEndAt()))
                    .status(coupon.getStatus())
                    .build());
        }

        return responses;
    }

    public CouponInfoResponse getInfo(int couponId) {
        Coupon coupon                       = this.couponRepository.findCoupon(couponId);
        //TODO CreateCouponResponse로 변경
        //TODO couponProducts 세팅
        List<CouponProduct> couponProducts  = this.couponRepository.findCouponProducts(couponId);
        coupon.setCouponProducts(couponProducts);

        return CouponInfoResponse.builder()
                .coupon(coupon)
                .products(couponProducts)
                .build();
    }

    @Transactional
    public boolean create(CreateCouponRequest couponRequest) {
        //Coupon 빌더
        Coupon coupon = Coupon.builder()
                .name(couponRequest.getName())
                .description(couponRequest.getDescription())
                .couponType(couponRequest.getCouponType())
                .discountType(couponRequest.getDiscountType())
                .discountAmount(couponRequest.getDiscountAmount())
                .applyStartAt(couponRequest.getApplyStartDate())
                .applyEndAt(couponRequest.getApplyEndDate())
                .minApplyPrice(couponRequest.getMinApplyPrice())
                .memberShipGrades(couponRequest.getMemberShipGrades())
                .availableQuantity(couponRequest.getAvailableQuantity())
                .usedQuantity(0)
                .status("active")
                .createdBy("admin") // TODO: 추후 로그인한 사용자로 변경
                .updatedBy("admin") // TODO: 추후 로그인한 사용자로 변경
                .couponCode(couponRequest.getCouponCode())
                .issuedType(couponRequest.getIssuedType())
                .build();

        int couponId = this.couponRepository.insertCoupon(coupon);

        if(couponId > 0) {
            //TODO 엑셀파일 읽고 couponProducts 세팅

            //this.couponRepository.insertCouponProducts(couponProducts);
        }

        return true;
    }
}
