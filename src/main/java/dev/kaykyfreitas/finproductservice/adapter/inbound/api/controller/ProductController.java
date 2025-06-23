package dev.kaykyfreitas.finproductservice.adapter.inbound.api.controller;

import dev.kaykyfreitas.finproductservice.adapter.inbound.api.ProductAPI;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.request.CreateProductRequest;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.request.UpdateProductRequest;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.CreateProductResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.GetProductByIdResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.ListProductResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.UpdateProductResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.presenter.ProductApiPresenter;
import dev.kaykyfreitas.finproductservice.application.usecase.product.create.CreateProductCommand;
import dev.kaykyfreitas.finproductservice.application.usecase.product.create.CreateProductUseCase;
import dev.kaykyfreitas.finproductservice.application.usecase.product.delete.DeleteProductUseCase;
import dev.kaykyfreitas.finproductservice.application.usecase.product.retrieve.get.GetProductByIdUseCase;
import dev.kaykyfreitas.finproductservice.application.usecase.product.retrieve.list.ListProductUseCase;
import dev.kaykyfreitas.finproductservice.application.usecase.product.update.UpdateProductCommand;
import dev.kaykyfreitas.finproductservice.application.usecase.product.update.UpdateProductUseCase;
import dev.kaykyfreitas.finproductservice.domain.pagination.Pagination;
import dev.kaykyfreitas.finproductservice.domain.pagination.SearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductAPI {

    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final ListProductUseCase listProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    @Override
    public ResponseEntity<CreateProductResponse> create(final CreateProductRequest request) {
        final var command = CreateProductCommand.with(
                request.name(),
                request.description(),
                request.price(),
                request.categoryId()
        );

        final var output = createProductUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(ProductApiPresenter.present(output));
    }

    @Override
    public ResponseEntity<UpdateProductResponse> update(final String id, final UpdateProductRequest request) {
        final var command = UpdateProductCommand.with(
                id,
                request.name(),
                request.description(),
                request.price(),
                request.categoryId()
        );

        final var output = updateProductUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.OK).body(ProductApiPresenter.present(output));
    }

    @Override
    public ResponseEntity<GetProductByIdResponse> getById(final String id) {
        final var output = getProductByIdUseCase.execute(id);

        return ResponseEntity.status(HttpStatus.OK).body(ProductApiPresenter.present(output));
    }

    @Override
    public ResponseEntity<Pagination<ListProductResponse>> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        final var query = new SearchQuery(page, perPage, search, sort, direction);

        final var output = listProductUseCase.execute(query);

        return ResponseEntity.status(HttpStatus.OK).body(output.map(ProductApiPresenter::present));
    }

    @Override
    public ResponseEntity<Void> delete(final String id) {
        deleteProductUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

}
