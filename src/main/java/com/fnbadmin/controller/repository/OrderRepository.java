package com.fnbadmin.controller.repository;

import com.fnbadmin.controller.request.OrderRequest;
import com.fnbadmin.domain.Member;
import com.fnbadmin.domain.Order;
import com.fnbadmin.domain.OrderAdditionalOption;
import com.fnbadmin.domain.OrderProduct;
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
public class OrderRepository {

    private final EntityManager em;

    public OrderRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Long getTotalOrderCount(OrderRequest orderRequest) {
        CriteriaBuilder cb          = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq      = cb.createQuery(Long.class);
        Root<Order> root            = cq.from(Order.class);

        cq = cq.where(cb.and(this.buildConditions(orderRequest, cb, root).toArray(new Predicate[0])));
        cq = cq.select((cb.count(root)));

        return  em.createQuery(cq).getSingleResult();
    }

    public List<Order> findOrders(OrderRequest orderRequest) {
        CriteriaBuilder cb         = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq    = cb.createQuery(Order.class);
        Root<Order> root           = cq.from(Order.class);

        cq = cq.where(cb.and(this.buildConditions(orderRequest, cb, root).toArray(new Predicate[0])));

        TypedQuery<Order> typedQuery = em.createQuery(cq);
        typedQuery.setFirstResult(orderRequest.getPage() - 1);
        typedQuery.setMaxResults(orderRequest.getPageLimit());

        return typedQuery.getResultList();
    }

    public Order findOrder(String orderId) {
        CriteriaBuilder cb         = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq    = cb.createQuery(Order.class);
        Root<Order> root           = cq.from(Order.class);

        cq = cq.where(cb.and(cb.equal(root.get("orderId"), orderId)));
        TypedQuery<Order> typedQuery = em.createQuery(cq);

        return typedQuery.getSingleResult();
    }

    public List<OrderProduct> findOrderProducts(String orderId) {
        CriteriaBuilder cb                = em.getCriteriaBuilder();
        CriteriaQuery<OrderProduct> cq    = cb.createQuery(OrderProduct.class);
        Root<OrderProduct> root           = cq.from(OrderProduct.class);

        cq = cq.where(cb.and(cb.equal(root.get("orderId"), orderId)));

        TypedQuery<OrderProduct> typedQuery = em.createQuery(cq);

        return typedQuery.getResultList();
    }

    public List<OrderAdditionalOption> findOrderAdditionalOptions(List<String> orderProductIds) {
        CriteriaBuilder cb                          = em.getCriteriaBuilder();
        CriteriaQuery<OrderAdditionalOption> cq     = cb.createQuery(OrderAdditionalOption.class);
        Root<OrderAdditionalOption> root            = cq.from(OrderAdditionalOption.class);

        cq = cq.where(cb.and(root.get("orderProductId").in(orderProductIds)));

        TypedQuery<OrderAdditionalOption> typedQuery = em.createQuery(cq);

        return typedQuery.getResultList();
    }

    private List<Predicate> buildConditions(OrderRequest orderRequest, CriteriaBuilder cb, Root<Order> root) {
        List<Predicate> searchConditions    = new ArrayList<>();
        String searchType                   = orderRequest.getSearchType();
        String searchWord                   = orderRequest.getSearchWord();

        if(orderRequest.getOrderStartDate() != null && orderRequest.getOrderEndDate() != null){
            searchConditions.add(cb.between(root.get("orderDate"), orderRequest.getOrderStartDate(), orderRequest.getOrderEndDate()));
        }

        if(orderRequest.getOrderStatus() != null && !orderRequest.getOrderStatus().isEmpty()){
            searchConditions.add(cb.and(root.get("orderStatus").in(orderRequest.getOrderStatus())));
        }

        if(orderRequest.getOrderType() != null && !orderRequest.getOrderType().isEmpty()){
            searchConditions.add(cb.and(root.get("orderType").in(orderRequest.getOrderType())));
        }

        if(orderRequest.getMemberSeq() > 0){
            searchConditions.add(cb.equal(root.get("memberSeq"), orderRequest.getMemberSeq()));
        }

        if (searchWord != null && !searchWord.trim().isEmpty()) {
            if ("memberId".equals(searchType)) {
                searchConditions.add(cb.like(root.get("memberId"), "%" + searchWord + "%"));
            } else if ("orderId".equals(searchType)) {
                searchConditions.add(cb.equal(root.get("orderId"), searchWord));
            }
        }

        return  searchConditions;
    }
}
