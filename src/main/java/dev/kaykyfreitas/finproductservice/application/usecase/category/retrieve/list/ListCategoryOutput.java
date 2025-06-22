package dev.kaykyfreitas.finproductservice.application.usecase.category.retrieve.list;

import dev.kaykyfreitas.finproductservice.domain.category.Category;

import java.time.Instant;

public record ListCategoryOutput(
        String id,
        String name,
        String description,
        boolean active,
        Instant createdAt
) {
    public static ListCategoryOutput from(final Category category) {
        return new ListCategoryOutput(
                category.getId().getValue(),
                category.getName(),
                category.getDescription(),
                category.isActive(),
                category.getCreatedAt()
        );
    }
}
