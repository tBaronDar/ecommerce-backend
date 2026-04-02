package com.tBaronDar.Ecom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    Product product;
    int quantity;
    BigDecimal totalPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    Order order;
}
