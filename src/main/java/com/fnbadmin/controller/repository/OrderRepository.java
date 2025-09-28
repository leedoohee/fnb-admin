package com.fnbadmin.controller.repository;

import com.fnbadmin.domain.Order;
import com.fnbadmin.domain.OrderAdditionalOption;
import com.fnbadmin.domain.OrderProduct;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderRepository {

    private final EntityManager em;

    public OrderRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public List<Order> findOrders(String startDate, String endDate, String status, int page, int pageLimit) {
        return this.em.createQuery("select o from Order o where o.orderDate between :startDate and :endDate", Order.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public Order findOrder(String orderId) {
        return this.em.createQuery("select o from Order o where o.orderId = :orderId", Order.class)
                .setParameter("orderId", orderId)
                .getSingleResult();
    }

    public List<OrderProduct> findOrderProducts(String orderIds) {
        return this.em.createQuery("select op from OrderProduct op where op.orderId in (:orderIds)", OrderProduct.class)
                .setParameter("orderIds", orderIds)
                .getResultList();
    }

    public List<OrderAdditionalOption> findOrderAdditionalOptions(String orderProductIds) {
        return this.em.createQuery("select oao from OrderAdditionalOption oao where oao.orderProductId in (:orderProductIds)", OrderAdditionalOption.class)
                .setParameter("orderProductIds", orderProductIds)
                .getResultList();
    }
}
