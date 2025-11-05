package com.course.tcc.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.course.tcc.domain.Order;
import com.course.tcc.domain.Product;
import com.course.tcc.infrastructure.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BadOrderService {

    private final OrderRepository repository;

    public void createOrder(final Order order) {

        final List<Product> products = order.getProducts();

        products.forEach(product -> {

            if (product.getStockQuantity() <= 0) {
                throw new RuntimeException("Stock to " + product.getName() + "is empty.");
            }

        });

        final BigDecimal totalOrder = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Total: " + totalOrder);
        repository.save(order);
    }

}
