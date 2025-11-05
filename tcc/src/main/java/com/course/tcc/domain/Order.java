package com.course.tcc.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @ManyToMany
    private List<Product> products = new ArrayList<>();

    public boolean isCanceled() {
        return OrderStatus.CANCELED.equals(status);
    }

    public boolean isFinalized() {
        return OrderStatus.FINALIZED.equals(status);
    }

    public void markAsFinalized() {

        if (OrderStatus.PENDING.equals(status)) {
            status = OrderStatus.FINALIZED;
        }

    }

    public BigDecimal calculeTotal() {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void validateStock() {

        products.forEach(product -> {

            if (product.getStockQuantity() <= 0) {
                throw new RuntimeException("Stock to " + product.getName() + "is empty.");
            }

        });

    }

    public void markAsPending() {
        validateStock();
        status = OrderStatus.PENDING;
    }
}
