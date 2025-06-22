package dev.kaykyfreitas.finproductservice.application.usecase.category.delete;

import dev.kaykyfreitas.finproductservice.domain.category.CategoryGateway;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final CategoryGateway categoryGateway;

    @Override
    public void execute(final String id) {
        final var categoryId = CategoryId.from(id);
        categoryGateway.delete(categoryId);
    }

}
