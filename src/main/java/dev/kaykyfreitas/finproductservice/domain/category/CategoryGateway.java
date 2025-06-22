package dev.kaykyfreitas.finproductservice.domain.category;

import dev.kaykyfreitas.finproductservice.domain.pagination.Pagination;
import dev.kaykyfreitas.finproductservice.domain.pagination.SearchQuery;

import java.util.Optional;

public interface CategoryGateway {
    Category create(Category category);
    Optional<Category> getById(CategoryId id);
    Pagination<Category> getAll(SearchQuery query);
    Category update(Category category);
    void delete(CategoryId id);
}
