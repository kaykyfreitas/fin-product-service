package dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response;

import java.time.Instant;

public record ListCategoryResponse(
        String id,
        String name,
        String description,
        boolean active,
        Instant createdAt
) {
}
