package dev.kaykyfreitas.finproductservice.adapter.outbound.jpa.mapper;

import dev.kaykyfreitas.finproductservice.adapter.outbound.jpa.entity.CategoryJpaEntity;
import dev.kaykyfreitas.finproductservice.domain.category.Category;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;

public final class CategoryJpaMapper {

    private CategoryJpaMapper() {}

    public static Category fromJpa(final CategoryJpaEntity categoryJpaEntity) {
        if (categoryJpaEntity == null) return null;

        return Category.with(
                CategoryId.from(categoryJpaEntity.getId()),
                categoryJpaEntity.getName(),
                categoryJpaEntity.getDescription(),
                categoryJpaEntity.isActive(),
                categoryJpaEntity.getCreatedAt(),
                categoryJpaEntity.getUpdatedAt(),
                categoryJpaEntity.getDeletedAt()
        );
    }

    public static CategoryJpaEntity toJpa(final Category category) {
        if (category == null) return null;

        return new CategoryJpaEntity(
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
