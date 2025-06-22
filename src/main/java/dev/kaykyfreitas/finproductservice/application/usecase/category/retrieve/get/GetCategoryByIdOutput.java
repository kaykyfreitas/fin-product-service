package dev.kaykyfreitas.finproductservice.application.usecase.category.retrieve.get;

import dev.kaykyfreitas.finproductservice.domain.category.Category;

import java.time.Instant;

public record GetCategoryByIdOutput(
        String id,
        String name,
        String description,
        boolean active,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static GetCategoryByIdOutput from(final Category category) {
        return new GetCategoryByIdOutput(
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
