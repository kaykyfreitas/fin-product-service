package dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.request;

public record UpdateCategoryRequest(
        String name,
        String description
) {
}
