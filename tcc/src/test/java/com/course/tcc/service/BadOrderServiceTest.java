package com.course.tcc.service;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.course.tcc.domain.Client;
import com.course.tcc.domain.Order;
import com.course.tcc.domain.Product;
import com.course.tcc.infrastructure.OrderRepository;

class BadOrderServiceTest {

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private BadOrderService service;

    private Order order;
    private Product p2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Client client = new Client();
        client.setName("João");

        Product p1 = new Product();
        p1.setName("Produto 1");
        p1.setPrice(new BigDecimal("10.50"));
        p1.setStockQuantity(5L);

        p2 = new Product();
        p2.setName("Produto 2");
        p2.setPrice(new BigDecimal("20.00"));
        p2.setStockQuantity(3L);

        order = new Order();
        order.setClient(client);
        order.setProducts(Arrays.asList(p1, p2));
    }

    @Test
    void testCreateOrderSuccess() {
        service.createOrder(order);
        Mockito.verify(repository, Mockito.times(1)).save(order);
    }

    @Test
    void testCreateOrderStockFail() {
        p2.setStockQuantity(0L);
        RuntimeException exception = null;

        try {
            service.createOrder(order);
        } catch (RuntimeException e) {
            exception = e;
        }

        assert exception != null;
        assert exception.getMessage().equals("Stock to Produto 2is empty.");
        Mockito.verify(repository, Mockito.never()).save(order);
    }
}