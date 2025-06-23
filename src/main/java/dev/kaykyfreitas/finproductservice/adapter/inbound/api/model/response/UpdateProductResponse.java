package dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response;

import java.math.BigDecimal;
import java.time.Instant;

public record UpdateProductResponse(
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
}
