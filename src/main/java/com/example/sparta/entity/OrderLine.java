package com.example.sparta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "ORDER_LINES")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // 주문 상세 PK

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;   // 주문

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;   // 상품

    @Column(name = "amount")
    private Long amount;   // 주문 개수

    public OrderLine(Order order, Product product, Long amount) {
        this.order = order;
        this.product = product;
        this.amount = amount;
        // Order <-> OrderLine 양방향 매핑
        this.order.addOrderline(this);
    }
}