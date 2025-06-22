package dev.kaykyfreitas.finproductservice.application.usecase.category.create;

import dev.kaykyfreitas.finproductservice.domain.category.Category;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryGateway;
import dev.kaykyfreitas.finproductservice.domain.exception.NotificationException;
import dev.kaykyfreitas.finproductservice.domain.validation.handler.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    @Override
    public CreateCategoryOutput execute(final CreateCategoryCommand command) {
        final var name = command.name();
        final var description = command.description();

        final var notification = Notification.create();
        final var category = notification.validate(() -> Category.newCategory(name, description));

        if (notification.hasError()) notify(notification);

        return CreateCategoryOutput.from(categoryGateway.create(category));
    }

    private void notify(final Notification notification) {
        throw new NotificationException("could not create a category", notification);
    }

}
