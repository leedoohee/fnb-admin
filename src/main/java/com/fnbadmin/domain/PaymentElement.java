package com.fnbadmin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PaymentElement {

    @Id
    private int id;
    private int paymentId;
    private String paymentMethod; // 카드, 현금, 포인트 등
    private String amount; // 결제 금액
    private String cardType; // 신용카드, 체크카드 등 (카드 결제 시)
    private String cardNumber; // 카드 번호 (카드 결제 시)
    private String bankName; // 은행 이름 (계좌 이체 시)
    private String accountNumber; // 계좌 번호 (계좌 이체 시)
    private String accountType;
}
