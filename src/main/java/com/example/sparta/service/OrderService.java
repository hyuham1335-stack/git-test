package com.example.sparta.service;

import com.example.sparta.dto.OrderLineRequest;
import com.example.sparta.entity.Order;
import com.example.sparta.entity.OrderLine;
import com.example.sparta.entity.Product;
import com.example.sparta.repository.OrderLineRepository;
import com.example.sparta.repository.OrderRepository;
import com.example.sparta.repository.ProductRepository;
import com.example.sparta.dto.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineService orderLineService;

    @Transactional
    public Order create(OrderCreateRequest request) {
        // 주문 데이터 생성
        Order order = orderRepository.save(new Order(request.getTotalPrice()));

        // 주문 상세 데이터 생성
        orderLineService.createOrderLines(order, request.getOrderLines());

        return order;
    }
}