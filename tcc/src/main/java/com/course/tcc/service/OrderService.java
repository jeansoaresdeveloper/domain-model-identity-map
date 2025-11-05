package com.course.tcc.service;

import org.springframework.stereotype.Service;

import com.course.tcc.domain.Order;
import com.course.tcc.infrastructure.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    public void createOrder(final Order order) {
        order.markAsPending();
        repository.save(order);
    }

}
