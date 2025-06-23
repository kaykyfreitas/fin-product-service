package dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.request;

public record CreateCategoryRequest(
        String name,
        String description
) {
}
