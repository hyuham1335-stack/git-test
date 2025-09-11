package com.example.sparta.service;

import com.example.sparta.dto.OrderLineRequest;
import com.example.sparta.entity.Order;
import com.example.sparta.entity.OrderLine;
import com.example.sparta.entity.Product;
import com.example.sparta.repository.OrderLineRepository;
import com.example.sparta.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final ProductRepository productRepository;
    private final OrderLineRepository orderLineRepository;

    public List<OrderLine> createOrderLines(
        Order order,
        List<OrderLineRequest> orderLineRequests
    ) {
        List<OrderLine> orderLineList = new ArrayList<>();
        for (OrderLineRequest olr : orderLineRequests) {
            Product product = productRepository.findById(olr.getProductId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품은 주문할 수 없습니다 !"));

            // 상품 구매 처리
            product.purchased(olr.getAmount());

            // 주문 상세 데이터 생성
            orderLineList.add(new OrderLine(order, product, olr.getAmount()));
        }
        return orderLineRepository.saveAll(orderLineList);
    }
}