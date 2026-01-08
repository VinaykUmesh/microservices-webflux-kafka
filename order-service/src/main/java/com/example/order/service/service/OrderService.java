package com.example.order.service.service;

import com.example.order.service.model.Order;
import com.example.order.service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    public Order placeOrder(Order order) {
        return repository.save(order);
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }
}
