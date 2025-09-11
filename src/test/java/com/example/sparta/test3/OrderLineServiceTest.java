package com.example.sparta.test3;

import com.example.sparta.dto.OrderLineRequest;
import com.example.sparta.entity.Order;
import com.example.sparta.entity.OrderLine;
import com.example.sparta.entity.Product;
import com.example.sparta.repository.OrderLineRepository;
import com.example.sparta.repository.ProductRepository;
import com.example.sparta.service.OrderLineService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderLineServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderLineRepository orderLineRepository;
    @InjectMocks
    private OrderLineService orderLineService;


    @Test
    public void 성공_주문_상세_생성_및_상품_구매_처리() {
        // given
        List<OrderLineRequest> orderLineRequests = List.of(
            new OrderLineRequest(1L, 10L),
            new OrderLineRequest(2L, 20L)
        );
        Product product1 = new Product(1L, "productName1", "description1", 1000L, 100L, 0L);
        Product product2 = new Product(2L, "productName2", "description1", 1000L, 100L, 0L);
        Order order = new Order(10000L);
        List<OrderLine> orderLines = List.of(
            new OrderLine(order, product1, 10L),
            new OrderLine(order, product2, 20L)
        );
        
        given(productRepository.findById(1L)).willReturn(Optional.of(product1));
        given(productRepository.findById(2L)).willReturn(Optional.of(product2));

        // when
        orderLineService.createOrderLines(order, orderLineRequests);

        // then
        assertThat(product1.getAmount()).isEqualTo(90L); // product1 재고 검증
        assertThat(product1.getSaleCount()).isEqualTo(10L); // product1 판매 횟수 검증
        assertThat(product2.getAmount()).isEqualTo(80L); // product2 재고 검증
        assertThat(product2.getSaleCount()).isEqualTo(20L); // product2 판매 횟수 검증

    }
}