package dev.kaykyfreitas.finproductservice.domain.product;

import dev.kaykyfreitas.finproductservice.domain.AggregateRoot;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.exception.NotificationException;
import dev.kaykyfreitas.finproductservice.domain.utils.InstantUtils;
import dev.kaykyfreitas.finproductservice.domain.validation.ValidationHandler;
import dev.kaykyfreitas.finproductservice.domain.validation.handler.Notification;

import java.math.BigDecimal;
import java.time.Instant;

public class Product extends AggregateRoot<ProductId> {

    private String name;
    private String description;
    private BigDecimal price;
    private ProductSku sku;
    private boolean active;
    private CategoryId categoryId;

    private Product(
            final ProductId productId,
            final String name,
            final String description,
            final BigDecimal price,
            final ProductSku sku,
            final boolean active,
            final CategoryId categoryId,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        super(productId, createdAt, updatedAt, deletedAt);
        this.name = name;
        this.description = description;
        this.price = price;
        this.sku = sku;
        this.active = active;
        this.categoryId = categoryId;
        this.selfValidation();
    }

    public static Product newProduct(
            final String name,
            final String description,
            final BigDecimal price,
            final CategoryId categoryId
    ) {
        final var productId = ProductId.unique();
        final var productSku = ProductSku.generate(name);
        final var now = InstantUtils.now();
        return new Product(
                productId,
                name,
                description,
                price,
                productSku,
                true,
                categoryId,
                now,
                now,
                null
        );
    }

    public static Product with(
            final ProductId productId,
            final String name,
            final String description,
            final BigDecimal price,
            final ProductSku sku,
            final boolean active,
            final CategoryId categoryId,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Product(
                productId,
                name,
                description,
                price,
                sku,
                active,
                categoryId,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new ProductValidator(this, handler).validate();
    }

    private void selfValidation() {
        final var notification = Notification.create();

        this.validate(notification);

        if (notification.hasError())
            throw new NotificationException("failed to create product", notification);
    }

    public Product activate() {
        this.active = true;
        this.deletedAt = null;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Product deactivate() {
        this.active = false;
        this.deletedAt = InstantUtils.now();
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Product update(
            final String name,
            final String description,
            final BigDecimal price,
            final ProductSku sku,
            final CategoryId categoryId
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.sku = sku;
        this.categoryId = categoryId;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductSku getSku() {
        return sku;
    }

    public boolean isActive() {
        return active;
    }

    public CategoryId getCategoryId() {
        return categoryId;
    }

}
