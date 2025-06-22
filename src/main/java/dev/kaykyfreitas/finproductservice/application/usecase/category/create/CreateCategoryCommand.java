package dev.kaykyfreitas.finproductservice.application.usecase.category.create;

public record CreateCategoryCommand(
        String name,
        String description
) {
    public static CreateCategoryCommand with(
            final String name,
            final String description
    ) {
        return new CreateCategoryCommand(name, description);
    }
}
