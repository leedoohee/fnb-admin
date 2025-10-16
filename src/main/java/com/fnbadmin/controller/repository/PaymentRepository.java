package com.fnbadmin.controller.repository;

import com.fnbadmin.domain.Payment;
import com.fnbadmin.domain.PaymentElement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentRepository {

    private final EntityManager em;

    public PaymentRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public List<Payment> findPayments(List<String> orderIds) {
        CriteriaBuilder cb            = em.getCriteriaBuilder();
        CriteriaQuery<Payment> cq     = cb.createQuery(Payment.class);
        Root<Payment> root            = cq.from(Payment.class);

        cq = cq.where(cb.and(root.get("orderId").in(orderIds)));

        TypedQuery<Payment> typedQuery = em.createQuery(cq);

        return typedQuery.getResultList();
    }

    public List<PaymentElement> findPaymentElements(String paymentIds) {
        CriteriaBuilder cb                  = em.getCriteriaBuilder();
        CriteriaQuery<PaymentElement> cq    = cb.createQuery(PaymentElement.class);
        Root<PaymentElement> root           = cq.from(PaymentElement.class);

        cq = cq.where(cb.and(root.get("paymentId").in(paymentIds)));

        TypedQuery<PaymentElement> typedQuery = em.createQuery(cq);

        return typedQuery.getResultList();
    }

    public Payment findPayment(String orderId) {
        CriteriaBuilder cb          = em.getCriteriaBuilder();
        CriteriaQuery<Payment> cq   = cb.createQuery(Payment.class);
        Root<Payment> root          = cq.from(Payment.class);

        cq = cq.where(cb.and(cb.equal(root.get("orderId"), orderId)));
        TypedQuery<Payment> typedQuery = em.createQuery(cq);

        return typedQuery.getSingleResult();
    }
}
