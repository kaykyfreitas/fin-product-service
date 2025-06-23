package dev.kaykyfreitas.finproductservice.application.usecase.product.retrieve.get;

import dev.kaykyfreitas.finproductservice.application.usecase.UseCaseTest;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.exception.DomainException;
import dev.kaykyfreitas.finproductservice.domain.product.Product;
import dev.kaykyfreitas.finproductservice.domain.product.ProductGateway;
import dev.kaykyfreitas.finproductservice.domain.product.ProductId;
import dev.kaykyfreitas.finproductservice.domain.product.ProductSku;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetProductByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetProductByIdUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway);
    }

    @Test
    void givenValidId_whenCallGetProductById_shouldReturnProduct() {
        // Given
        final var expectedId = ProductId.unique();
        final var expectedName = "Laptop";
        final var expectedDescription = "High-performance laptop";
        final var expectedPrice = BigDecimal.valueOf(1500.00);
        final var expectedSku = ProductSku.generate(expectedName);
        final var expectedCategoryId = CategoryId.unique();
        final var expectedCreatedAt = Instant.now();
        final var expectedUpdatedAt = Instant.now();

        final var aProduct = Product.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedPrice,
                expectedSku,
                true,
                expectedCategoryId,
                expectedCreatedAt,
                expectedUpdatedAt,
                null
        );

        when(productGateway.getById(expectedId)).thenReturn(Optional.of(aProduct));

        // When
        final var actualOutput = useCase.execute(expectedId.getValue());

        // Then
        assertNotNull(actualOutput);
        assertEquals(expectedId.getValue(), actualOutput.id());
        assertEquals(expectedName, actualOutput.name());
        assertEquals(expectedDescription, actualOutput.description());
        assertEquals(expectedPrice, actualOutput.price());
        assertEquals(expectedSku.getValue(), actualOutput.sku());
        assertEquals(expectedCategoryId.getValue(), actualOutput.categoryId());
        assertTrue(actualOutput.active());
        assertEquals(expectedCreatedAt, actualOutput.createdAt());
        assertEquals(expectedUpdatedAt, actualOutput.updatedAt());
        assertNull(actualOutput.deletedAt());

        verify(productGateway, times(1)).getById(expectedId);
        verifyNoMoreInteractions(productGateway);
    }

    @Test
    void givenInvalidId_whenCallGetProductById_shouldThrowDomainException() {
        // Given
        final var expectedId = ProductId.unique();
        final var expectedErrorMessage = "product with id %s was not found".formatted(expectedId.getValue());

        when(productGateway.getById(expectedId)).thenReturn(Optional.empty());

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(productGateway, times(1)).getById(expectedId);
        verifyNoMoreInteractions(productGateway);
    }

    @Test
    void givenValidId_whenGatewayThrowsRandomException_thenShouldThrowException() {
        // Given
        final var expectedId = ProductId.unique();
        final var expectedErrorMessage = "gateway error";

        doThrow(new IllegalStateException(expectedErrorMessage)).when(productGateway).getById(expectedId);

        // When
        final var actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(productGateway, times(1)).getById(expectedId);
        verifyNoMoreInteractions(productGateway);
    }
}
