package com.example.sparta.test1;

import com.example.sparta.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductTest {

    Product product;    // 1

    @BeforeEach     
    void initProduct() {      // 2
        product = new Product(1L, "티셔츠", "무지 티 입니다.", 10000L, 100L, 0L);
    }

    @Test
    public void 성공_상품_구매() {
        // when
        product.purchased(10L);

        // then
        assertThat(product.getAmount()).isEqualTo(90L);
        assertThat(product.getSaleCount()).isEqualTo(10L);
    }

    @Test
    public void 실패_상품_재고를_초과하는_구매() {    // 3
        // when
        RuntimeException e = assertThrows(RuntimeException.class,
            () -> product.purchased(200L));

        // then
        assertThat(e.getMessage()).isEqualTo("티셔츠의 상품 재고가 부족합니다.");
    }
}