package com.fnbadmin.controller.service;

import com.fnbadmin.controller.repository.OrderRepository;
import com.fnbadmin.controller.repository.PaymentRepository;
import com.fnbadmin.controller.request.OrderRequest;
import com.fnbadmin.controller.response.MemberListResponse;
import com.fnbadmin.controller.response.OrderInfoResponse;
import com.fnbadmin.controller.response.OrderListResponse;
import com.fnbadmin.controller.response.PageResponse;
import com.fnbadmin.domain.Order;
import com.fnbadmin.domain.OrderAdditionalOption;
import com.fnbadmin.domain.OrderProduct;
import com.fnbadmin.domain.Payment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public OrderService(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    public PageResponse<OrderListResponse> getList(OrderRequest orderRequest) {
        List<OrderListResponse> responses = new ArrayList<>();

        long totalCount     = this.orderRepository.getTotalOrderCount(orderRequest);
        List<Order> orders  = this.orderRepository.findOrders(orderRequest);

        int lastPageNumber  = (int) (Math.ceil((double) totalCount / orderRequest.getPageLimit()));

        List<String> orderIdList = orders.stream().map(Order::getOrderId).toList();
        List<Payment> payments   = this.paymentRepository.findPayments(orderIdList);

        for (Order order : orders) {
            payments.stream().filter(payment -> payment.getOrderId().equals(order.getOrderId()))
                    .findFirst()
                    .ifPresent(order::setPayment);
        }

        for(Order order : orders) {
            responses.add(OrderListResponse.builder()
                    .orderId(order.getOrderId())
                    .orderDate(String.valueOf(order.getOrderDate()))
                    .orderStatus(order.getOrderStatus())
                    .totalAmount(order.getTotalAmount())
                    .paymentType(order.getPayment() != null ? order.getPayment().getPaymentType() : null)
                    .paymentStatus(order.getPayment() != null ? order.getPayment().getPaymentStatus() : null)
                    .paymentAmount(order.getPayment() != null ? order.getPayment().getPaymentAmount() : null)
                    .build());
        }

        return PageResponse.<OrderListResponse>builder()
                .last_page(lastPageNumber)
                .data(responses)
                .build();
    }

    public OrderInfoResponse getInfo(String orderId) {
        Order order                      = this.orderRepository.findOrder(orderId);
        List<OrderProduct> orderProducts = this.orderRepository.findOrderProducts(orderId);
        Payment payment                  = this.paymentRepository.findPayment(orderId);
        List<String> orderProductIdList  = orderProducts.stream().map(OrderProduct::getOrderProductId).map(String::valueOf).toList();
        List<OrderAdditionalOption> orderAdditionalOptions = this.orderRepository.findOrderAdditionalOptions(orderProductIdList);

        for (OrderProduct orderProduct : orderProducts) {
            List<OrderAdditionalOption> additionalOptions = orderAdditionalOptions.stream()
                    .filter(orderAdditionalOption ->
                            orderAdditionalOption.getOrderProductId() == orderProduct.getOrderProductId())
                    .toList();

            orderProduct.setOrderAdditionalOptions(additionalOptions);
        }

        return OrderInfoResponse.builder()
                .orderId(order.getOrderId())
                .memberName(order.getMember() != null ? order.getMember().getName() : null)
                .orderStatus(order.getOrderStatus())
                .orderDate(String.valueOf(order.getOrderDate()))
                .totalAmount(order.getTotalAmount())
                .usePoint(order.getUsePoint())
                .couponAmount(BigDecimal.valueOf(order.getCouponAmount()))
                .discountAmount(order.getDiscountAmount())
                .paymentStatus(payment != null ? payment.getPaymentStatus() : null)
                .paymentDate(payment != null ? String.valueOf(payment.getPaymentAt()) : null)
                .paymentType(payment != null ? payment.getPaymentType() : null)
                .paymentAmount(payment != null ? payment.getPaymentAmount() : null)
                .orderProducts(orderProducts)
                .payment(payment)
                .build();
    }
}
