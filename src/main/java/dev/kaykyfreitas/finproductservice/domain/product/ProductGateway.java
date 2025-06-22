package dev.kaykyfreitas.finproductservice.domain.product;

import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.pagination.Pagination;
import dev.kaykyfreitas.finproductservice.domain.pagination.SearchQuery;

import java.util.Optional;

public interface ProductGateway {
    Product create(Product product);
    Optional<Product> getById(ProductId id);
    Pagination<Product> getAll(SearchQuery searchQuery);
    Pagination<Product> getAll(CategoryId categoryId, SearchQuery searchQuery);
    Product update(Product product);
    void delete(ProductId id);
}
