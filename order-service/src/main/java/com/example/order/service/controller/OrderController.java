package com.example.order.service.controller;

import com.example.events.OrderCreatedEvent;
import com.example.events.ProductDto;
import com.example.order.service.dto.OrderResponseDto;
import com.example.order.service.model.Order;
import com.example.order.service.service.OrderProducer;
import com.example.order.service.service.OrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final WebClient.Builder webclientBuilder;
    private final OrderProducer orderProducer;

    @PostMapping("/placeOrders")
    public Mono<@NonNull ResponseEntity<@NonNull OrderResponseDto>> placeOrder(@RequestBody Order order) {
        return webclientBuilder.build().get()
                .uri("http://PRODUCT-SERVICE/api/products/" + order.getProductId())
                .retrieve()
                .bodyToMono(ProductDto.class).map(productDto -> {
                    Order saved = orderService.placeOrder(order);
                    return ResponseEntity.ok().body(OrderResponseDto.builder()
                            .id(saved.getId())
                            .productId(saved.getProductId())
                            .quantity(saved.getQuantity())
                            .productName(productDto.getProductName())
                            .productPrice(productDto.getProductPrice())
                            .totalPrice(saved.getQuantity() * productDto.getProductPrice())
                            .build());
                });
    }

    @PostMapping("/kafka/placeOrders")
    public ResponseEntity<String> kafkaPlaceOrder(@RequestBody Order order) {
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(order.getId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .build();
        orderProducer.sendOrderCreatedEvent(event);
        return ResponseEntity.ok("Order placed successfully");
    }

    @GetMapping
    public ResponseEntity<@NonNull List<Order>> getAll() {
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }
}
