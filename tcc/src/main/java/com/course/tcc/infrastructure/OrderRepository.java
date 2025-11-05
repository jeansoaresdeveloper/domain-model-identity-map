package com.course.tcc.infrastructure;

import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.course.tcc.domain.Client;
import com.course.tcc.domain.Order;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;
    private final GenericIdentityMap<Order> identityMap;

    public Order findById(final Long id) {

        if (identityMap.contains(id)) {
            System.out.println("Returning cacheable order");
            return identityMap.get(id);
        }

        System.out.println("Searching order in Database");
        final Order order = em.find(Order.class, id);

        if (Objects.nonNull(order)) {
            identityMap.add(order.getId(), order);
        }

        return order;

    }

    public void save(final Order order) {
        em.persist(order);
    }
}
