package dev.kaykyfreitas.finproductservice.application.usecase.category.retrieve.list;

import dev.kaykyfreitas.finproductservice.application.UseCase;
import dev.kaykyfreitas.finproductservice.domain.pagination.Pagination;
import dev.kaykyfreitas.finproductservice.domain.pagination.SearchQuery;

public abstract class ListCategoryUseCase extends UseCase<SearchQuery, Pagination<ListCategoryOutput>> {
}
