package dev.kaykyfreitas.finproductservice.application.usecase.category.retrieve.get;

import dev.kaykyfreitas.finproductservice.domain.category.Category;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryGateway;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGateway categoryGateway;

    @Override
    public GetCategoryByIdOutput execute(final String id) {
        final var categoryId = CategoryId.from(id);

        final var category = categoryGateway.getById(categoryId)
                .orElseThrow(() -> NotFoundException.with(Category.class, categoryId));

        return GetCategoryByIdOutput.from(category);
    }

}
