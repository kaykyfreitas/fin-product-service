package dev.kaykyfreitas.finproductservice.application.usecase.category.update;

import dev.kaykyfreitas.finproductservice.domain.category.Category;

import java.time.Instant;

public record UpdateCategoryOutput(
        String id,
        String name,
        String description,
        boolean active,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static UpdateCategoryOutput from(final Category category) {
        return new UpdateCategoryOutput(
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
