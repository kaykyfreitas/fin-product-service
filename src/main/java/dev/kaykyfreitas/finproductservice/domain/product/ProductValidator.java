package dev.kaykyfreitas.finproductservice.domain.product;

import dev.kaykyfreitas.finproductservice.domain.validation.DomainError;
import dev.kaykyfreitas.finproductservice.domain.validation.ValidationHandler;
import dev.kaykyfreitas.finproductservice.domain.validation.Validator;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductValidator extends Validator {

    private static final Integer NAME_MAX_LENGTH = 100;
    private static final Integer NAME_MIN_LENGTH = 3;

    private static final Integer DESCRIPTION_MAX_LENGTH = 255;
    private static final Integer DESCRIPTION_MIN_LENGTH = 5;

    private static final Integer SKU_SIZE = 20;

    private final Product product;

    protected ProductValidator(final Product product, final ValidationHandler handler) {
        super(handler);
        this.product = product;
    }

    @Override
    public void validate() {
        validateNameConstraints();
        validateDescriptionConstraints();
        validateSkuConstraints();
        validatePriceConstraints();
        validateCategoryConstraints();
    }

    private void validateNameConstraints() {
        final var name = this.product.getName();
        if (Objects.isNull(name)) {
            this.validationHandler().append(new DomainError("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new DomainError("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            final String msg = "'name' must be between %d and %d characters";
            this.validationHandler().append(new DomainError(String.format(msg, NAME_MIN_LENGTH, NAME_MAX_LENGTH)));
        }
    }

    private void validateDescriptionConstraints() {
        final var description = this.product.getDescription();
        if (Objects.isNull(description)) {
            this.validationHandler().append(new DomainError("'description' should not be null"));
            return;
        }

        if (description.isBlank()) {
            this.validationHandler().append(new DomainError("'description' should not be empty"));
            return;
        }

        final int length = description.trim().length();
        if (length > DESCRIPTION_MAX_LENGTH || length < DESCRIPTION_MIN_LENGTH) {
            final String msg = "'description' must be between %d and %d characters";
            this.validationHandler().append(new DomainError(String.format(msg, DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH)));
        }
    }

    private void validateSkuConstraints() {
        final var sku = this.product.getSku();
        if (Objects.isNull(sku)) {
            this.validationHandler().append(new DomainError("'sku' should not be null"));
            return;
        }

        final var value = sku.getValue();
        if (Objects.isNull(value) || value.isBlank()) {
            this.validationHandler().append(new DomainError("'sku' should not be empty"));
            return;
        }

        if (!value.matches("^[A-Z0-9]+$")) {
            this.validationHandler().append(new DomainError("'sku' must contain only uppercase letters and numbers"));
            return;
        }

        if (value.length() != SKU_SIZE) {
            this.validationHandler().append(new DomainError(String.format("'sku' must be exactly %d characters", SKU_SIZE)));
        }
    }

    private void validatePriceConstraints() {
        final var price = this.product.getPrice();
        if (Objects.isNull(price)) {
            this.validationHandler().append(new DomainError("'price' should not be null"));
            return;
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            this.validationHandler().append(new DomainError("'price' must be greater than zero"));
        }
    }

    private void validateCategoryConstraints() {
        final var categoryId = this.product.getCategoryId();
        if (Objects.isNull(categoryId)) {
            this.validationHandler().append(new DomainError("'categoryId' should not be null"));
        }
    }

}
