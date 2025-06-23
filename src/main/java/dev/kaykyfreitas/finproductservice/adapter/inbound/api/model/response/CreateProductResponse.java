package dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateProductResponse(
        String id,
        String name,
        String description,
        BigDecimal price,
        String sku,
        boolean active,
        String categoryId,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
}
