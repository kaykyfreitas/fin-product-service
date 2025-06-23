package dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response;

import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.product.ProductSku;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateProductResponse(
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
}
