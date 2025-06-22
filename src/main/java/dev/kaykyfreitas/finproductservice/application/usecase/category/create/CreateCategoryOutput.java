package dev.kaykyfreitas.finproductservice.application.usecase.category.create;

import dev.kaykyfreitas.finproductservice.domain.category.Category;

import java.time.Instant;

public record CreateCategoryOutput(
        String id,
        String name,
        String description,
        boolean active,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(
                category.getId().getValue(),
                category.getName(),
                category.getDescription(),
                category.isActive(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getDeletedAt()
        );
    }
}
