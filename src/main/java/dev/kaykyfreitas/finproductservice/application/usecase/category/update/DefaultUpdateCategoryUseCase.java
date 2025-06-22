package dev.kaykyfreitas.finproductservice.application.usecase.category.update;

import dev.kaykyfreitas.finproductservice.domain.category.Category;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryGateway;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.exception.NotFoundException;
import dev.kaykyfreitas.finproductservice.domain.exception.NotificationException;
import dev.kaykyfreitas.finproductservice.domain.validation.handler.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    @Override
    public UpdateCategoryOutput execute(final UpdateCategoryCommand command) {
        final var categoryId = CategoryId.from(command.id());
        final var name = command.name();
        final var description = command.description();

        final var category = categoryGateway.getById(categoryId)
                .orElseThrow(() -> NotFoundException.with(Category.class, categoryId));

        final var notification = Notification.create();
        notification.validate(() -> category.update(name, description));

        if (notification.hasError()) notify(notification);

        return UpdateCategoryOutput.from(categoryGateway.update(category));
    }

    private void notify(final Notification notification) {
        throw new NotificationException("could not update category", notification);
    }

}
