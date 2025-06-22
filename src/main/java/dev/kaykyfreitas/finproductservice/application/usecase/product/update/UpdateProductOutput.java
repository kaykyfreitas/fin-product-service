package dev.kaykyfreitas.finproductservice.application.usecase.product.update;

import dev.kaykyfreitas.finproductservice.domain.product.Product;

import java.math.BigDecimal;
import java.time.Instant;

public record UpdateProductOutput(
        String id,
        String name,
        String description,
        String sku,
        BigDecimal price,
        boolean active,
        String categoryId,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static UpdateProductOutput from(Product product) {
        return new UpdateProductOutput(
            product.getId().getValue(),
            product.getName(),
            product.getDescription(),
            product.getSku().getValue(),
            product.getPrice(),
            product.isActive(),
            product.getCategoryId() != null ? product.getCategoryId().getValue() : null,
            product.getCreatedAt(),
            product.getUpdatedAt(),
            product.getDeletedAt()
        );
    }
}
