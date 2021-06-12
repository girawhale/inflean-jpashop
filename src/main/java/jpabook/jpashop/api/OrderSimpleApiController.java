package jpabook.jpashop.api;

import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

/*
xToOne(ManyToOne, OneToOne)
Order
Order -> Member
Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        // 양방향 연관관계면 무한 루프에 걸리지 않게 한곳에 @JsonIgnore 를 추가해야 함
        // 엔티티를 API 응답으로 외부로 노출하는 것은 좋지 않기 때문에 Hibernate5Module 를 사용하기 보다는 DTO로 변환해서 반환하는 것이 더 좋은 방법
        for (Order order : orders) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기환
        }
        return orders;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        // Order 2개
        // N + 1 문제 발생
        // 쿼리가 총 1 + N + N번 실행된다. (v1과 쿼리수 결과는 같다.)
        //  order 조회 1번(order 조회 결과 수가 N이 된다.)
        //  order -> member 지연 로딩 조회 N 번
        //  order -> delivery 지연 로딩 조회 N 번
        // 지연로딩은 영속성 컨텍스트에서 조회하므로, 이미 조회된 경우 쿼리를 생략
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        return orders.stream()
                .map(SimpleOrderDto::new)
                .collect(toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream()
                .map(SimpleOrderDto::new)
                .collect(toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        // select 컬럼이 줄어든다는 장점이 있음.
        // 엄청 많을 때는 장점이 있지만, 일반적으로는 join과 from때문에 영향이 있음.
        // 재사용성이 많이 떨어진다
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
    }

}
