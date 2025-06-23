package dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.request;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String name,
        String description,
        BigDecimal price,
        String categoryId
) {
}
