package dev.kaykyfreitas.finproductservice.application.usecase.product.create;

import java.math.BigDecimal;

public record CreateProductCommand(
        String name,
        String description,
        BigDecimal price,
        String categoryId
) {
    public static CreateProductCommand with(
            final String name,
            final String description,
            final BigDecimal price,
            final String categoryId
    ) {
        return new CreateProductCommand(name, description, price, categoryId);
    }
}
