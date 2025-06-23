package dev.kaykyfreitas.finproductservice.application.usecase.product.retrieve.get;

import dev.kaykyfreitas.finproductservice.domain.product.Product;

import java.math.BigDecimal;
import java.time.Instant;

public record GetProductByIdOutput(
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
    public static GetProductByIdOutput from(Product product) {
        return new GetProductByIdOutput(
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
