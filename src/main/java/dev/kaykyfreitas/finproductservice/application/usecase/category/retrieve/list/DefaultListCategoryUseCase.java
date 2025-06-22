package dev.kaykyfreitas.finproductservice.application.usecase.category.retrieve.list;

import dev.kaykyfreitas.finproductservice.domain.category.CategoryGateway;
import dev.kaykyfreitas.finproductservice.domain.pagination.Pagination;
import dev.kaykyfreitas.finproductservice.domain.pagination.SearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultListCategoryUseCase extends ListCategoryUseCase {

    private final CategoryGateway categoryGateway;

    @Override
    public Pagination<ListCategoryOutput> execute(final SearchQuery query) {
        return categoryGateway.getAll(query)
                .map(ListCategoryOutput::from);
    }

}
