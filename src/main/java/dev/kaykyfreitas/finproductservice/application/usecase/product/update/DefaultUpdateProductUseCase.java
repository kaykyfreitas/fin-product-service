package dev.kaykyfreitas.finproductservice.application.usecase.product.update;

import dev.kaykyfreitas.finproductservice.domain.category.Category;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryGateway;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.exception.NotFoundException;
import dev.kaykyfreitas.finproductservice.domain.exception.NotificationException;
import dev.kaykyfreitas.finproductservice.domain.product.Product;
import dev.kaykyfreitas.finproductservice.domain.product.ProductGateway;
import dev.kaykyfreitas.finproductservice.domain.product.ProductId;
import dev.kaykyfreitas.finproductservice.domain.validation.handler.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultUpdateProductUseCase extends UpdateProductUseCase {

    private final ProductGateway productGateway;
    private final CategoryGateway categoryGateway;

    @Override
    public UpdateProductOutput execute(final UpdateProductCommand command) {
        final var productId = ProductId.from(command.id());
        final var name = command.name();
        final var description = command.description();
        final var price = command.price();
        final var categoryId = CategoryId.from(command.categoryId());

        final var product = productGateway.getById(productId)
                .orElseThrow(() -> NotFoundException.with(Product.class, productId));

        categoryGateway.getById(categoryId)
                .orElseThrow(() -> NotFoundException.with(Category.class, categoryId));

        final var notification = Notification.create();
        notification.validate(() -> product.update(name, description, price, categoryId));

        if (notification.hasError()) notify(notification);

        return UpdateProductOutput.from(productGateway.update(product));
    }

    private void notify(final Notification notification) {
        throw new NotificationException("could not update product", notification);
    }
}
