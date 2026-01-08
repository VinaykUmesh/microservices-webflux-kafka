package com.example.product.service.service;

import com.example.events.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderConsumer {

    private final ProductService productService;

    @KafkaListener(
            topics = "order-created",
            groupId = "product-service-group"
    )
    public void consume(OrderCreatedEvent event) {
        System.out.println("🔥 Product Service received event: " + event.toString());
    }
}
