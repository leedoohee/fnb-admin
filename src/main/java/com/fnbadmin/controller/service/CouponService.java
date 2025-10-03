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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    public CouponService(CouponRepository couponRepository, MemberRepository memberRepository) {
        this.couponRepository = couponRepository;
        this.memberRepository = memberRepository;
    }

    public PageResponse<CouponListResponse>  getList(CouponRequest couponRequest) {
        List<CouponListResponse> responses = new ArrayList<>();
        long totalCount         = this.couponRepository.getTotalCouponCount(couponRequest);
        List<Coupon> coupons    = this.couponRepository.findCoupons(couponRequest);

        int lastPageNumber  = (int) (Math.ceil((double) totalCount / couponRequest.getPageLimit()));

        List<Integer> couponIdList          = coupons.stream().map(Coupon::getId).toList();
        List<MemberCoupon> memberCoupons    = this.memberRepository.findMemberCoupons(couponIdList);
        List<CouponProduct> couponProducts  = this.couponRepository.findCouponProducts(couponIdList);

        for (Coupon coupon : coupons) {
            List<CouponProduct> relatedProducts = couponProducts.stream()
                    .filter(cp -> cp.getCouponId() == coupon.getId())
                    .toList();

            List<MemberCoupon> ownedCoupons = memberCoupons.stream()
                    .filter(mc -> mc.getCouponId() == coupon.getId())
                    .toList();

            coupon.setCouponProducts(relatedProducts);
            coupon.setMemberCoupons(ownedCoupons);
        }

        for (Coupon coupon : coupons) {
            responses.add(CouponListResponse.builder()
                    .couponId(coupon.getId())
                    .description(coupon.getDescription())
                    .applyStartDate(String.valueOf(coupon.getApplyStartAt()))
                    .applyEndDate(String.valueOf(coupon.getApplyEndAt()))
                    .status(coupon.getStatus())
                    .couponName(coupon.getName())
                    .couponProductCount(coupon.getCouponProducts().size())
                    .usedMemberCount(coupon.getMemberCoupons().stream().filter(cp -> cp.getIsUsed().equals("Y")).toList().size())
                    .nonUsedMemberCount(coupon.getMemberCoupons().stream().filter(cp -> cp.getIsUsed().equals("N")).toList().size())
                    .build());
        }

        return PageResponse.<CouponListResponse>builder()
                .last_page(lastPageNumber)
                .data(responses)
                .build();
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
