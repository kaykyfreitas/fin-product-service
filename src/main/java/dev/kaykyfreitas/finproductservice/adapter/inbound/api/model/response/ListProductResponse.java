package dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response;

import java.math.BigDecimal;
import java.time.Instant;

public record ListProductResponse(
        String id,
        String name,
        String sku,
        BigDecimal price,
        boolean active,
        String categoryId,
        Instant createdAt
) {
}
