package com.fnbadmin.controller.service;

import com.fnbadmin.controller.repository.CouponRepository;
import com.fnbadmin.controller.repository.MemberRepository;
import com.fnbadmin.controller.request.CouponRequest;
import com.fnbadmin.controller.request.CreateCouponRequest;
import com.fnbadmin.controller.response.CouponInfoResponse;
import com.fnbadmin.controller.response.CouponListResponse;
import com.fnbadmin.controller.response.OrderListResponse;
import com.fnbadmin.controller.response.PageResponse;
import com.fnbadmin.domain.Coupon;
import com.fnbadmin.domain.CouponProduct;
import com.fnbadmin.domain.MemberCoupon;
import com.fnbadmin.util.CouponStatus;
import com.fnbadmin.util.Used;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public PageResponse<CouponListResponse>  getCoupons(CouponRequest couponRequest) {
        List<CouponListResponse> responses = new ArrayList<>();
        long totalCount         = this.couponRepository.getTotalCouponCount(couponRequest);
        List<Coupon> coupons    = this.couponRepository.findCoupons(couponRequest);

        int lastPageNumber  = (int) (Math.ceil((double) totalCount / couponRequest.getPageLimit()));

        for (Coupon coupon : coupons) {
            responses.add(CouponListResponse.builder()
                    .couponId(coupon.getCouponId())
                    .description(coupon.getDescription())
                    .applyStartDate(String.valueOf(coupon.getApplyStartAt()))
                    .applyEndDate(String.valueOf(coupon.getApplyEndAt()))
                    .status(coupon.getStatus())
                    .couponName(coupon.getName())
                    .couponProductCount(coupon.getCouponProducts().size())
                    .usedMemberCount(coupon.getUsedCount())
                    .nonUsedMemberCount(coupon.getPublishCount() - coupon.getUsedCount())
                    .build());
        }

        return PageResponse.<CouponListResponse>builder()
                .last_page(lastPageNumber)
                .data(responses)
                .build();
    }

    public CouponInfoResponse getInfo(int couponId) {
        Coupon coupon = this.couponRepository.findCoupon(couponId);

        assert coupon != null: "쿠폰이 존재하지 않습니다.";

        //TODO CreateCouponResponse로 변경
        //TODO couponProducts 세팅
        List<CouponProduct> couponProducts  = coupon.getCouponProducts();

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
                .amount(couponRequest.getDiscountAmount())
                .applyStartAt(couponRequest.getApplyStartDate())
                .applyEndAt(couponRequest.getApplyEndDate())
                .minApplyPrice(couponRequest.getMinApplyPrice())
                .memberShipGrades(couponRequest.getMemberShipGrades())
                .availableQuantity(couponRequest.getAvailableQuantity())
                .usedQuantity(0)
                .status(CouponStatus.AVAILABLE.getValue())
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
