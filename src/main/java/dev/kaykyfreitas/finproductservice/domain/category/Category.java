package dev.kaykyfreitas.finproductservice.domain.category;

import dev.kaykyfreitas.finproductservice.domain.AggregateRoot;
import dev.kaykyfreitas.finproductservice.domain.exception.NotificationException;
import dev.kaykyfreitas.finproductservice.domain.utils.InstantUtils;
import dev.kaykyfreitas.finproductservice.domain.validation.ValidationHandler;
import dev.kaykyfreitas.finproductservice.domain.validation.handler.Notification;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryId> {

    private String name;
    private String description;
    private boolean active;

    public Category(
            CategoryId categoryId,
            String name,
            String description,
            boolean active,
            Instant createdAt,
            Instant updatedAt,
            Instant deletedAt
    ) {
        super(categoryId, createdAt, updatedAt, deletedAt);
        this.name = name;
        this.description = description;
        this.active = active;
        this.selfValidation();
    }

    public static Category newCategory(
            final String name,
            final String description
    ) {
        final CategoryId categoryId = CategoryId.unique();
        final Instant now = InstantUtils.now();
        return new Category(
                categoryId,
                name,
                description,
                true,
                now,
                now,
                null
        );
    }

    public static Category with(
            final CategoryId categoryId,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Category(
                categoryId,
                name,
                description,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    private void selfValidation() {
        final var notification = Notification.create();

        this.validate(notification);

        if (notification.hasError())
            throw new NotificationException("failed to create category", notification);
    }

    public Category activate() {
        this.active = true;
        this.deletedAt = null;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Category deactivate() {
        this.active = false;
        this.deletedAt = InstantUtils.now();
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Category update(
            final String name,
            final String description
    ) {
        this.name = name;
        this.description = description;
        this.updatedAt = InstantUtils.now();
        this.selfValidation();
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

}
