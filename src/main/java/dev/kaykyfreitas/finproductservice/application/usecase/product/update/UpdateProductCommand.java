package dev.kaykyfreitas.finproductservice.application.usecase.product.update;

import java.math.BigDecimal;

public record UpdateProductCommand(
        String id,
        String name,
        String description,
        BigDecimal price,
        String categoryId
) {
    public static UpdateProductCommand with(
            final String id,
            final String name,
            final String description,
            final BigDecimal price,
            final String categoryId
    ) {
        return new UpdateProductCommand(id, name, description, price, categoryId);
    }
}
