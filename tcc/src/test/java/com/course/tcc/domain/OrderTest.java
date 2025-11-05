package com.course.tcc.domain;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

    private Product p1;
    private Product p2;

    @BeforeEach
    void setUp() {
        Client client = new Client();
        client.setName("João");

        p1 = new Product();
        p1.setName("Produto 1");
        p1.setPrice(new BigDecimal("10.50"));
        p1.setStockQuantity(5L);

        p2 = new Product();
        p2.setName("Produto 2");
        p2.setPrice(new BigDecimal("20.00"));
        p2.setStockQuantity(3L);
    }

    @Test
    void testCalculeTotal() {
        Order order = new Order();
        order.setProducts(Arrays.asList(p1, p2));

        BigDecimal total = order.calculeTotal();
        assertEquals(new BigDecimal("30.50"), total);
    }

    @Test
    void testValidateStockOk() {
        Order order = new Order();
        order.setProducts(Arrays.asList(p1, p2));

        assertDoesNotThrow(order::validateStock);
    }

    @Test
    void testValidateStockFail() {
        p2.setStockQuantity(0L);
        Order order = new Order();
        order.setProducts(Arrays.asList(p1, p2));

        RuntimeException exception = assertThrows(RuntimeException.class, order::validateStock);
        assertEquals("Stock to Produto 2is empty.", exception.getMessage());
    }

    @Test
    void testMarkAsPending() {
        Order order = new Order();
        order.setProducts(Arrays.asList(p1, p2));
        order.markAsPending();

        assertEquals(OrderStatus.PENDING, order.getStatus());
    }

    @Test
    void testMarkAsFinalized() {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);

        order.markAsFinalized();
        assertEquals(OrderStatus.FINALIZED, order.getStatus());
    }
}
