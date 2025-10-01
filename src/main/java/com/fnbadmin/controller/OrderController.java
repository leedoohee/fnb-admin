package com.fnbadmin.controller;

import com.fnbadmin.controller.request.OrderRequest;
import com.fnbadmin.controller.response.OrderInfoResponse;
import com.fnbadmin.controller.response.OrderListResponse;
import com.fnbadmin.controller.response.PageResponse;
import com.fnbadmin.controller.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public String order(){
        return "order.html";
    }

    @GetMapping("/order/list")
    public ResponseEntity<PageResponse<OrderListResponse>> getOrders(OrderRequest orderRequest) {
        return ResponseEntity.ok(this.orderService.getList(orderRequest));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderInfoResponse> getInfo(@PathVariable String orderId) {
        return ResponseEntity.ok(this.orderService.getInfo(orderId));
    }
}
