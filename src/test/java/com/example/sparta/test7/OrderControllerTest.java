package com.example.sparta.test7;

import com.example.sparta.controller.OrderController;
import com.example.sparta.dto.OrderCreateRequest;
import com.example.sparta.dto.OrderLineRequest;
import com.example.sparta.entity.Order;
import com.example.sparta.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(OrderController.class) // 컨트롤러만 떼어내서 테스트
@ActiveProfiles("test") // application-test.yml 설정 적용
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // 객체 -> JSON 변환기

    @MockitoBean // [최신] 서비스는 가짜 객체로 대체
    private OrderService orderService;

    @Test
    void 주문_요청_성공_시_200_OK_반환() throws Exception {
        // given
        // 1. 요청 데이터 생성 (DTO 구조에 맞춤)
        List<OrderLineRequest> orderLines = List.of(
                new OrderLineRequest(1L, 2L), // 상품ID 1, 수량 2
                new OrderLineRequest(2L, 1L)  // 상품ID 2, 수량 1
        );
        OrderCreateRequest request = new OrderCreateRequest(15000L, orderLines);

        Order fakeOrder = new Order(15000L); // Order 객체 생성 (ID는 Setter나 Reflection 필요할 수 있음)

        given(orderService.create(any(OrderCreateRequest.class))).willReturn(fakeOrder);

        // when & then
        mockMvc.perform(
                post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON) // "나 JSON 보낸다!"
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print()) // 콘솔에 로그 출력
                .andExpect(status().isOk()); // 200 OK 확인
    }

    @Test
    void 주문_금액이_0원이면_400_Bad_Request() throws Exception {
        // given
        OrderCreateRequest request = new OrderCreateRequest(0L, List.of());

        // when & then
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest()); // 400 에러 확인
    }
}