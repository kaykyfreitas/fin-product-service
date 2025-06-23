package dev.kaykyfreitas.finproductservice.application.usecase.product.retrieve.list;

import dev.kaykyfreitas.finproductservice.domain.product.ProductGateway;
import dev.kaykyfreitas.finproductservice.domain.pagination.Pagination;
import dev.kaykyfreitas.finproductservice.domain.pagination.SearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultListProductUseCase extends ListProductUseCase {

    private final ProductGateway productGateway;

    @Override
    public Pagination<ListProductOutput> execute(final SearchQuery query) {
        return productGateway.getAll(query)
                .map(ListProductOutput::from);
    }
}
