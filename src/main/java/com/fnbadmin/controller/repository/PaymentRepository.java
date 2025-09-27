package com.fnbadmin.controller.repository;

import com.fnbadmin.domain.Order;
import com.fnbadmin.domain.Payment;
import com.fnbadmin.domain.PaymentElement;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentRepository {

    private EntityManager em;

    public PaymentRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public List<Payment> findPayments(String orderIds) {
        return this.em.createQuery("select p from Payment p where p.orderId in (:orderIds)", Payment.class)
                .setParameter("orderIds", orderIds)
                .getResultList();
    }

    public List<PaymentElement> findPaymentElements(String paymentIds) {
        return this.em.createQuery("select pe from PaymentElement pe where pe.paymentId in (:paymentIds) ", PaymentElement.class)
                .setParameter("paymentIds", paymentIds)
                .getResultList();
    }

    public Payment findPayment(String orderId) {
        return this.em.createQuery("select p from Payment p where p.orderId = :orderId", Payment.class)
                .setParameter("orderId", orderId)
                .getSingleResult();
    }
}
