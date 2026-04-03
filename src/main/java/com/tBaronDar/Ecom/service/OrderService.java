package com.tBaronDar.Ecom.service;

import com.tBaronDar.Ecom.model.Order;
import com.tBaronDar.Ecom.model.OrderItem;
import com.tBaronDar.Ecom.model.Product;
import com.tBaronDar.Ecom.model.dto.OrderItemRequest;
import com.tBaronDar.Ecom.model.dto.OrderItemResponse;
import com.tBaronDar.Ecom.model.dto.OrderRequest;
import com.tBaronDar.Ecom.model.dto.OrderResponse;
import com.tBaronDar.Ecom.repo.OrderRepo;
import com.tBaronDar.Ecom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private OrderRepo orderRepo;

    public OrderResponse placeOrder(OrderRequest orderReq) {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString().toUpperCase());
        order.setCustomerName(orderReq.customerName());
        order.setEmail(orderReq.email());
        order.setStatus("placed");
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest oir : orderReq.items()) {
            //search for the product requested
            Product product = productRepo.findById(oir.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            //update stock
            product.setStockQuantity(product.getStockQuantity() - oir.quantity());
            //save to db
            productRepo.save(product);

            //create item using builder pattern
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(oir.quantity())
                    .order(order)
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(oir.quantity())))
                    .build();
            //add the created item to the list
            orderItems.add(orderItem);

        }
        //last thing, put the items in the order
        order.setItems(orderItems);
        //save to db
        Order savedOrder = orderRepo.save(order);

        List<OrderItemResponse> itemResponses = new ArrayList<>();
        for (OrderItem oi : savedOrder.getItems()) {
            OrderItemResponse itemResponse = new OrderItemResponse(
                    oi.getProduct().getName(),
                    oi.getQuantity(),
                    oi.getTotalPrice()
            );
        }

        OrderResponse orderResponse = new OrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getCustomerName(),
                savedOrder.getEmail(),
                savedOrder.getStatus(),
                savedOrder.getOrderDate(),
                itemResponses);
        return orderResponse;
    }

    public List<OrderResponse> getAllOrderResponses() {
        List<Order> orders = orderRepo.findAll();

        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order o : orders) {

            List<OrderItemResponse> orderItemResponses = new ArrayList<>();
            for (OrderItem oi : o.getItems()) {
                OrderItemResponse oir = new OrderItemResponse(
                        oi.getProduct().getName(),
                        oi.getQuantity(),
                        oi.getTotalPrice()
                );
                orderItemResponses.add(oir);
            }

            OrderResponse or = new OrderResponse(
                    o.getOrderId(),
                    o.getCustomerName(),
                    o.getEmail(),
                    o.getStatus(),
                    o.getOrderDate(),
                    orderItemResponses
            );
            orderResponses.add(or);

        }
        return orderResponses;
    }
}
