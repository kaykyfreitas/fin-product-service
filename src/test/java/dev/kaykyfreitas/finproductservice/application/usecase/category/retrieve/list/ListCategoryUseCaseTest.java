package dev.kaykyfreitas.finproductservice.application.usecase.category.retrieve.list;

import dev.kaykyfreitas.finproductservice.application.usecase.UseCaseTest;
import dev.kaykyfreitas.finproductservice.domain.category.Category;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryGateway;
import dev.kaykyfreitas.finproductservice.domain.pagination.Pagination;
import dev.kaykyfreitas.finproductservice.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ListCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway);
    }

    @Test
    void givenValidQuery_whenCallsListCategories_shouldReturnAll() {
        // Given
        final var category1 = Category.newCategory(
                "Electronics",
                "Devices and gadgets"
        );

        final var category2 = Category.newCategory(
                "Books",
                "Literature and educational materials"
        );

        final var categories = List.of(category1, category2);

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedTotal = 2;

        final var expectedItems = categories.stream()
                .map(ListCategoryOutput::from)
                .toList();

        final var expectedPagination = new Pagination<>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                categories
        );

        when(categoryGateway.getAll(any()))
                .thenReturn(expectedPagination);

        final var query = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // When
        final var actualOutput = useCase.execute(query);

        // Then
        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
        Assertions.assertEquals(expectedItems, actualOutput.items());

        verify(categoryGateway, times(1)).getAll(any());
        verifyNoMoreInteractions(categoryGateway);
    }

    @Test
    void givenValidQuery_whenCallsListCategoriesAndIsEmpty_shouldReturnEmpty() {
        // Given
        final var users = List.<Category>of();

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedTotal = 0;

        final var expectedItems = List.<ListCategoryOutput>of();

        final var expectedPagination = new Pagination<>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                users
        );

        when(categoryGateway.getAll(any()))
                .thenReturn(expectedPagination);

        final var query = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // When
        final var actualOutput = useCase.execute(query);

        // Then
        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
        Assertions.assertEquals(expectedItems, actualOutput.items());

        verify(categoryGateway, times(1)).getAll(any());
        verifyNoMoreInteractions(categoryGateway);
    }

    @Test
    void givenValidQuery_whenCallsListUsersAndGatewayThrowsAnException_shouldReturnError() {
        // Given
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "jo";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";

        final var expectedErrorMessage = "Gateway error";

        when(categoryGateway.getAll(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var query = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // When
        final var actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(query)
        );

        // Then
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(categoryGateway, times(1)).getAll(any());
        verifyNoMoreInteractions(categoryGateway);
    }
}
