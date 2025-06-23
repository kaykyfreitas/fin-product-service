package dev.kaykyfreitas.finproductservice.adapter.inbound.api.presenter;

import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.CreateCategoryResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.GetCategoryByIdResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.ListCategoryResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.UpdateCategoryResponse;
import dev.kaykyfreitas.finproductservice.application.usecase.category.create.CreateCategoryOutput;
import dev.kaykyfreitas.finproductservice.application.usecase.category.retrieve.get.GetCategoryByIdOutput;
import dev.kaykyfreitas.finproductservice.application.usecase.category.retrieve.list.ListCategoryOutput;
import dev.kaykyfreitas.finproductservice.application.usecase.category.update.UpdateCategoryOutput;

public interface CategoryApiPresenter {

    static CreateCategoryResponse present(final CreateCategoryOutput output) {
        return new CreateCategoryResponse(
                output.id(),
                output.name(),
                output.description(),
                output.active(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static UpdateCategoryResponse present(final UpdateCategoryOutput output) {
        return new UpdateCategoryResponse(
                output.id(),
                output.name(),
                output.description(),
                output.active(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static GetCategoryByIdResponse present(final GetCategoryByIdOutput output) {
        return new GetCategoryByIdResponse(
                output.id(),
                output.name(),
                output.description(),
                output.active(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static ListCategoryResponse present(final ListCategoryOutput output) {
        return new ListCategoryResponse(
                output.id(),
                output.name(),
                output.description(),
                output.active(),
                output.createdAt()
        );
    }

}
