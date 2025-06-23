package dev.kaykyfreitas.finproductservice.adapter.inbound.api.presenter;

import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.CreateProductResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.GetProductByIdResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.ListProductResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.UpdateProductResponse;
import dev.kaykyfreitas.finproductservice.application.usecase.product.create.CreateProductOutput;
import dev.kaykyfreitas.finproductservice.application.usecase.product.retrieve.get.GetProductByIdOutput;
import dev.kaykyfreitas.finproductservice.application.usecase.product.retrieve.list.ListProductOutput;
import dev.kaykyfreitas.finproductservice.application.usecase.product.update.UpdateProductOutput;

public interface ProductApiPresenter {

    static CreateProductResponse present(final CreateProductOutput output) {
        return new CreateProductResponse(
                output.id(),
                output.name(),
                output.description(),
                output.price(),
                output.sku().getValue(),
                output.active(),
                output.categoryId().getValue(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static UpdateProductResponse present(final UpdateProductOutput output) {
        return new UpdateProductResponse(
                output.id(),
                output.name(),
                output.description(),
                output.sku(),
                output.price(),
                output.active(),
                output.categoryId(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static GetProductByIdResponse present(final GetProductByIdOutput output) {
        return new GetProductByIdResponse(
                output.id(),
                output.name(),
                output.description(),
                output.sku(),
                output.price(),
                output.active(),
                output.categoryId(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static ListProductResponse present(final ListProductOutput output) {
        return new ListProductResponse(
                output.id(),
                output.name(),
                output.sku(),
                output.price(),
                output.active(),
                output.categoryId(),
                output.createdAt()
        );
    }
}
