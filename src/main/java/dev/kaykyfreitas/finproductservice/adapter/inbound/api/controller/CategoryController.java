package dev.kaykyfreitas.finproductservice.adapter.inbound.api.controller;

import dev.kaykyfreitas.finproductservice.adapter.inbound.api.CategoryAPI;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.request.CreateCategoryRequest;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.request.UpdateCategoryRequest;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.CreateCategoryResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.GetCategoryByIdResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.ListCategoryResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.UpdateCategoryResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.presenter.CategoryApiPresenter;
import dev.kaykyfreitas.finproductservice.application.usecase.category.create.CreateCategoryCommand;
import dev.kaykyfreitas.finproductservice.application.usecase.category.create.CreateCategoryUseCase;
import dev.kaykyfreitas.finproductservice.application.usecase.category.delete.DeleteCategoryUseCase;
import dev.kaykyfreitas.finproductservice.application.usecase.category.retrieve.get.GetCategoryByIdUseCase;
import dev.kaykyfreitas.finproductservice.application.usecase.category.retrieve.list.ListCategoryUseCase;
import dev.kaykyfreitas.finproductservice.application.usecase.category.update.UpdateCategoryCommand;
import dev.kaykyfreitas.finproductservice.application.usecase.category.update.UpdateCategoryUseCase;
import dev.kaykyfreitas.finproductservice.domain.pagination.Pagination;
import dev.kaykyfreitas.finproductservice.domain.pagination.SearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final ListCategoryUseCase listCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    @Override
    public ResponseEntity<CreateCategoryResponse> create(final CreateCategoryRequest request) {
        final var command = CreateCategoryCommand.with(
                request.name(),
                request.description()
        );

        final var output = createCategoryUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryApiPresenter.present(output));
    }

    @Override
    public ResponseEntity<UpdateCategoryResponse> update(final String id, final UpdateCategoryRequest request) {
        final var command = UpdateCategoryCommand.with(
                id,
                request.name(),
                request.description()
        );

        final var output = updateCategoryUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.OK).body(CategoryApiPresenter.present(output));
    }

    @Override
    public ResponseEntity<GetCategoryByIdResponse> getById(final String id) {
        final var output = getCategoryByIdUseCase.execute(id);

        return ResponseEntity.status(HttpStatus.OK).body(CategoryApiPresenter.present(output));
    }

    @Override
    public ResponseEntity<Pagination<ListCategoryResponse>> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        final var query = new SearchQuery(page, perPage, search, sort, direction);

        final var output = listCategoryUseCase.execute(query);

        return ResponseEntity.status(HttpStatus.OK).body(output.map(CategoryApiPresenter::present));
    }

    @Override
    public ResponseEntity<Void> delete(final String id) {
        deleteCategoryUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
