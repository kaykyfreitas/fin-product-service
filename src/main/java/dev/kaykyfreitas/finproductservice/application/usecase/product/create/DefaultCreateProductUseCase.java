package dev.kaykyfreitas.finproductservice.application.usecase.product.create;

import dev.kaykyfreitas.finproductservice.domain.category.Category;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryGateway;
import dev.kaykyfreitas.finproductservice.domain.exception.NotFoundException;
import dev.kaykyfreitas.finproductservice.domain.product.Product;
import dev.kaykyfreitas.finproductservice.domain.product.ProductGateway;
import dev.kaykyfreitas.finproductservice.domain.product.ProductSku;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.exception.NotificationException;
import dev.kaykyfreitas.finproductservice.domain.validation.handler.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DefaultCreateProductUseCase extends CreateProductUseCase {

    private final ProductGateway productGateway;
    private final CategoryGateway categoryGateway;

    @Override
    public CreateProductOutput execute(final CreateProductCommand command) {
        final var name = command.name();
        final var description = command.description();
        final var price = command.price();
        final var categoryId = CategoryId.from(command.categoryId());

        categoryGateway.getById(categoryId)
                .orElseThrow(() -> NotFoundException.with(Category.class, categoryId));

        final var notification = Notification.create();
        final var product = notification.validate(() -> Product.newProduct(name, description, price, categoryId));

        if (notification.hasError()) notify(notification);

        return CreateProductOutput.from(productGateway.create(product));
    }

    private void notify(final Notification notification) {
        throw new NotificationException("could not create a product", notification);
    }

}
