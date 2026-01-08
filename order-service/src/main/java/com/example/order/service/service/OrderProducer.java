package com.example.order.service.service;

import com.example.events.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderProducer {

    @Autowired
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        kafkaTemplate.send("order-created", event);
    }

}
