package dev.kaykyfreitas.finproductservice.application.usecase.product.retrieve.list;

import dev.kaykyfreitas.finproductservice.application.usecase.UseCaseTest;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.pagination.Pagination;
import dev.kaykyfreitas.finproductservice.domain.pagination.SearchQuery;
import dev.kaykyfreitas.finproductservice.domain.product.Product;
import dev.kaykyfreitas.finproductservice.domain.product.ProductGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListProductUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListProductUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway);
    }

    @Test
    void givenValidQuery_whenCallsListProducts_shouldReturnAll() {
        // Given
        final var laptop = Product.newProduct(
                "PRODUCT123",
                "Laptop",
                BigDecimal.valueOf(1500.00),
                CategoryId.unique()
        );

        final var phone = Product.newProduct(
                "PRODUCT456",
                "Phone",
                BigDecimal.valueOf(800.00),
                CategoryId.unique()
        );

        final var products = List.of(laptop, phone);

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedTotal = 2;

        final var expectedItems = products.stream()
                .map(ListProductOutput::from)
                .toList();

        final var expectedPagination = new Pagination<>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                products
        );

        when(productGateway.getAll(any()))
                .thenReturn(expectedPagination);

        final var query = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // When
        final var actualOutput = useCase.execute(query);

        // Then
        assertNotNull(actualOutput);
        assertEquals(expectedPage, actualOutput.currentPage());
        assertEquals(expectedPerPage, actualOutput.perPage());
        assertEquals(expectedTotal, actualOutput.total());
        assertEquals(expectedItems, actualOutput.items());

        verify(productGateway, times(1)).getAll(any());
        verifyNoMoreInteractions(productGateway);
    }

    @Test
    void givenNoProducts_whenCallsListProducts_shouldReturnEmptyPagination() {
        // Given
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedTotal = 0;

        final var expectedPagination = new Pagination<>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                List.<Product>of()
        );

        when(productGateway.getAll(any()))
                .thenReturn(expectedPagination);

        final var query = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // When
        final var actualOutput = useCase.execute(query);

        // Then
        assertNotNull(actualOutput);
        assertEquals(expectedPage, actualOutput.currentPage());
        assertEquals(expectedPerPage, actualOutput.perPage());
        assertEquals(expectedTotal, actualOutput.total());
        assertTrue(actualOutput.items().isEmpty());

        verify(productGateway, times(1)).getAll(any());
        verifyNoMoreInteractions(productGateway);
    }

    @Test
    void givenGatewayThrowsException_whenCallsListProducts_shouldThrowException() {
        // Given
        final var expectedErrorMessage = "gateway error";

        doThrow(new IllegalStateException(expectedErrorMessage)).when(productGateway).getAll(any());

        final var query = new SearchQuery(0, 10, "", "createdAt", "asc");

        // When
        final var actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(query)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(productGateway, times(1)).getAll(any());
        verifyNoMoreInteractions(productGateway);
    }
}
