package com.example.sparta.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderCreateRequest {
    private Long totalPrice;
    private List<OrderLineRequest> orderLines;

}
