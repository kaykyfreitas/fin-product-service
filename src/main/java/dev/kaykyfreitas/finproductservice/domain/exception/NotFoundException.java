package dev.kaykyfreitas.finproductservice.domain.exception;

import dev.kaykyfreitas.finproductservice.domain.Entity;
import dev.kaykyfreitas.finproductservice.domain.Identifier;
import dev.kaykyfreitas.finproductservice.domain.validation.DomainError;

import java.util.List;

public class NotFoundException extends DomainException{

    protected NotFoundException(final String aMessage, final List<DomainError> someErrors) {
        super(aMessage, someErrors);
    }

    public static NotFoundException with(
            final Class<? extends Entity<?>> entity,
            final Identifier id
    ) {
        final var errorMessage = "%s with id %s was not found".formatted(
                entity.getSimpleName().toLowerCase(),
                id.getValue()
        );
        final var domainError = new DomainError(errorMessage);
        return new NotFoundException(errorMessage, List.of(domainError));
    }

}
