package com.tBaronDar.Ecom.controller;

import com.tBaronDar.Ecom.model.dto.OrderRequest;
import com.tBaronDar.Ecom.model.dto.OrderResponse;
import com.tBaronDar.Ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService os;

    @PostMapping("/orders/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderReq){
        OrderResponse placedOrder= os.placeOrder(orderReq);
        return new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        List<OrderResponse> responses = os.getAllOrderResponses();
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }

}
