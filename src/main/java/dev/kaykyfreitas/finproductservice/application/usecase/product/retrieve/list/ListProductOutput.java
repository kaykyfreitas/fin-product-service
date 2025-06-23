package dev.kaykyfreitas.finproductservice.application.usecase.product.retrieve.list;

import dev.kaykyfreitas.finproductservice.domain.product.Product;

import java.math.BigDecimal;
import java.time.Instant;

public record ListProductOutput(
        String id,
        String name,
        String sku,
        BigDecimal price,
        boolean active,
        String categoryId,
        Instant createdAt
) {
    public static ListProductOutput from(Product product) {
        return new ListProductOutput(
            product.getId().getValue(),
            product.getName(),
            product.getSku().getValue(),
            product.getPrice(),
            product.isActive(),
            product.getCategoryId() != null ? product.getCategoryId().getValue() : null,
            product.getCreatedAt()
        );
    }
}
