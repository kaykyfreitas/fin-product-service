package dev.kaykyfreitas.finproductservice.domain.category;

import dev.kaykyfreitas.finproductservice.domain.validation.DomainError;
import dev.kaykyfreitas.finproductservice.domain.validation.ValidationHandler;
import dev.kaykyfreitas.finproductservice.domain.validation.Validator;

import java.util.Objects;

public class CategoryValidator extends Validator {

    private static final Integer NAME_MAX_LENGTH = 100;
    private static final Integer NAME_MIN_LENGTH = 3;

    private static final Integer DESCRIPTION_MAX_LENGTH = 255;
    private static final Integer DESCRIPTION_MIN_LENGTH = 5;

    private final Category category;

    protected CategoryValidator(final Category category, final ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        validateNameConstraints();
        validateDescriptionConstraints();
    }

    private void validateNameConstraints() {
        final var name = this.category.getName();
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
        final var description = this.category.getDescription();
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

}
