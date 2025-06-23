package dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response;

import dev.kaykyfreitas.finproductservice.domain.validation.DomainError;

import java.time.Instant;
import java.util.List;

public record DefaultErrorResponse(
        Instant timestamp,
        Integer status,
        List<DomainError> errors
) {
}
