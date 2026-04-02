package com.tBaronDar.Ecom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    int id;
    @ManyToOne
    Product product;
    int quantity;
    BigDecimal totalPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    Order order;
}
