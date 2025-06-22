package dev.kaykyfreitas.finproductservice.application.usecase.product.create;

import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.product.Product;
import dev.kaykyfreitas.finproductservice.domain.product.ProductSku;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateProductOutput(
        String id,
        String name,
        String description,
        BigDecimal price,
        ProductSku sku,
        boolean active,
        CategoryId categoryId,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static CreateProductOutput from(Product product) {
        return new CreateProductOutput(
                product.getId().getValue(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getSku(),
                product.isActive(),
                product.getCategoryId(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getDeletedAt()
        );
    }
}
