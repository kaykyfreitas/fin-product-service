package dev.kaykyfreitas.finproductservice.application.usecase.product.retrieve.get;

import dev.kaykyfreitas.finproductservice.domain.product.Product;
import dev.kaykyfreitas.finproductservice.domain.product.ProductGateway;
import dev.kaykyfreitas.finproductservice.domain.product.ProductId;
import dev.kaykyfreitas.finproductservice.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultGetProductByIdUseCase extends GetProductByIdUseCase {

    private final ProductGateway productGateway;

    @Override
    public GetProductByIdOutput execute(final String id) {
        final var productId = ProductId.from(id);

        final var product = productGateway.getById(productId)
                .orElseThrow(() -> NotFoundException.with(Product.class, productId));

        return GetProductByIdOutput.from(product);
    }
}
