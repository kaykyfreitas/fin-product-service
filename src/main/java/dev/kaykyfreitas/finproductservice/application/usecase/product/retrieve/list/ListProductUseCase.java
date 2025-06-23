package dev.kaykyfreitas.finproductservice.application.usecase.product.retrieve.list;

import dev.kaykyfreitas.finproductservice.application.UseCase;
import dev.kaykyfreitas.finproductservice.domain.pagination.Pagination;
import dev.kaykyfreitas.finproductservice.domain.pagination.SearchQuery;

public abstract class ListProductUseCase extends UseCase<SearchQuery, Pagination<ListProductOutput>> {
}
