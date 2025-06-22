package dev.kaykyfreitas.finproductservice.application.usecase.product.create;

import dev.kaykyfreitas.finproductservice.application.usecase.UseCaseTest;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryGateway;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.exception.NotificationException;
import dev.kaykyfreitas.finproductservice.domain.product.ProductGateway;
import dev.kaykyfreitas.finproductservice.domain.product.ProductSku;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

class CreateProductUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateProductUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway, categoryGateway);
    }

    @Test
    void givenValidCommand_whenCallCreateProduct_shouldReturnProduct() {
        // Given
        final var expectedName = "Laptop";
        final var expectedDescription = "High-performance laptop";
        final var expectedPrice = BigDecimal.valueOf(1500.00);
        final var expectedCategoryId = CategoryId.unique();

        final var aCommand = CreateProductCommand.with(
                expectedName,
                expectedDescription,
                expectedPrice,
                expectedCategoryId.getValue()
        );

        when(productGateway.create(any())).thenAnswer(returnsFirstArg());

        // When
        final var actualOutput = useCase.execute(aCommand);

        // Then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());
        assertEquals(expectedName, actualOutput.name());
        assertEquals(expectedDescription, actualOutput.description());
        assertEquals(expectedPrice, actualOutput.price());
        assertNotNull(actualOutput.sku());
        assertEquals(expectedCategoryId, actualOutput.categoryId());
        assertTrue(actualOutput.active());
        assertNotNull(actualOutput.createdAt());
        assertNotNull(actualOutput.updatedAt());
        assertEquals(actualOutput.createdAt(), actualOutput.updatedAt());
        assertNull(actualOutput.deletedAt());

        verify(productGateway, times(1)).create(any());
        verifyNoMoreInteractions(productGateway);
    }

    @Test
    void givenInvalidCommand_whenCallCreateProduct_shouldReceiveError() {
        // Given
        final var expectedName = "";
        final var expectedDescription = "High-performance laptop";
        final var expectedPrice = BigDecimal.valueOf(1500.00);
        final var expectedCategoryId = CategoryId.unique();

        final var aCommand = CreateProductCommand.with(
                expectedName,
                expectedDescription,
                expectedPrice,
                expectedCategoryId.getValue()
        );

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> useCase.execute(aCommand)
        );

        // Then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

        verifyNoMoreInteractions(productGateway);
    }

    @Test
    void givenValidCommand_whenGatewayThrowsRandomException_thenShouldThrowException() {
        // Given
        final var expectedName = "Laptop";
        final var expectedDescription = "High-performance laptop";
        final var expectedPrice = BigDecimal.valueOf(1500.00);
        final var expectedCategoryId = CategoryId.unique();

        final var aCommand = CreateProductCommand.with(
                expectedName,
                expectedDescription,
                expectedPrice,
                expectedCategoryId.getValue()
        );

        final var expectedErrorMessage = "gateway error";

        doThrow(new IllegalStateException(expectedErrorMessage)).when(productGateway).create(any());

        // When
        final var actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(aCommand)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(productGateway, times(1)).create(any());
        verifyNoMoreInteractions(productGateway);
    }
}
