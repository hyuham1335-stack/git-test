package com.example.sparta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderLineRequest {
    private Long productId;
    private Long amount;

}
