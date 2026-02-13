package com.example.sparta.controller;

import com.example.sparta.dto.OrderCreateRequest;
import com.example.sparta.entity.Order;
import com.example.sparta.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/orders")
    public ResponseEntity<Long> createOrder(@RequestBody OrderCreateRequest request) {
        if(request.getTotalPrice() <=0){
            return ResponseEntity.badRequest().build();
        }

        Order createOrder = orderService.create(request);

        return ResponseEntity.ok(createOrder.getId());
    }
}
