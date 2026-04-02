package com.tBaronDar.Ecom.model.dto;

import java.time.LocalDate;
import java.util.List;

public record OrderResponse(
        String customerName,
        String email,
        String orderId,
        String status,
        LocalDate orderDate,
        List<OrderItemResponse> items
) {}
