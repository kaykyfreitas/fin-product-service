package dev.kaykyfreitas.finproductservice.adapter.outbound.jpa.mapper;

import dev.kaykyfreitas.finproductservice.adapter.outbound.jpa.entity.ProductJpaEntity;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.product.Product;
import dev.kaykyfreitas.finproductservice.domain.product.ProductId;
import dev.kaykyfreitas.finproductservice.domain.product.ProductSku;

public final class ProductJpaMapper {

    private ProductJpaMapper() {}

    public static Product fromJpa(final ProductJpaEntity productJpaEntity) {
        if (productJpaEntity == null) return null;

        return Product.with(
                ProductId.from(productJpaEntity.getId()),
                productJpaEntity.getName(),
                productJpaEntity.getDescription(),
                productJpaEntity.getPrice(),
                ProductSku.from(productJpaEntity.getSku()),
                productJpaEntity.isActive(),
                CategoryId.from(productJpaEntity.getCategoryId()),
                productJpaEntity.getCreatedAt(),
                productJpaEntity.getUpdatedAt(),
                productJpaEntity.getDeletedAt()
        );
    }

    public static ProductJpaEntity toJpa(final Product product) {
        if (product == null) return null;

        return new ProductJpaEntity(
                product.getId().getValue(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getSku().getValue(),
                product.isActive(),
                product.getCategoryId().getValue(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getDeletedAt()
        );
    }

}
