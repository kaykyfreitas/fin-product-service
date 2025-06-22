package dev.kaykyfreitas.finproductservice.application.usecase.product.delete;

import dev.kaykyfreitas.finproductservice.domain.product.ProductGateway;
import dev.kaykyfreitas.finproductservice.domain.product.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultDeleteProductUseCase extends DeleteProductUseCase {

    private final ProductGateway productGateway;

    @Override
    public void execute(final String id) {
        final var productId = ProductId.from(id);
        productGateway.delete(productId);
    }
}
