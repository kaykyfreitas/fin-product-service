package dev.kaykyfreitas.finproductservice.application.usecase.product.update;

import dev.kaykyfreitas.finproductservice.application.usecase.UseCaseTest;
import dev.kaykyfreitas.finproductservice.domain.category.Category;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryGateway;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.exception.NotificationException;
import dev.kaykyfreitas.finproductservice.domain.product.Product;
import dev.kaykyfreitas.finproductservice.domain.product.ProductGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

class UpdateProductUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateProductUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway, categoryGateway);
    }

    @Test
    void givenValidCommand_whenCallUpdateProduct_shouldReturnUpdatedProduct() {
        // Given
        final var category = Category.newCategory("Electronics", "description for electronic products");

        final var expectedName = "Updated Laptop";
        final var expectedDescription = "Updated high-performance laptop";
        final var expectedPrice = BigDecimal.valueOf(1600.00);
        final var expectedCategoryId = category.getId();

        final var product = Product.newProduct(
                expectedName,
                expectedDescription,
                expectedPrice,
                expectedCategoryId
        );

        final var expectedId = product.getId().getValue();

        final var aCommand = UpdateProductCommand.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedPrice,
                expectedCategoryId.getValue()
        );

        when(productGateway.getById(any())).thenReturn(Optional.of(product));
        when(categoryGateway.getById(any())).thenReturn(Optional.of(category));
        when(productGateway.update(any())).thenAnswer(returnsFirstArg());

        // When
        final var actualOutput = useCase.execute(aCommand);

        // Then
        assertNotNull(actualOutput);
        assertEquals(expectedId, actualOutput.id());
        assertEquals(expectedName, actualOutput.name());
        assertEquals(expectedDescription, actualOutput.description());
        assertEquals(expectedPrice, actualOutput.price());
        assertEquals(product.getSku().getValue(), actualOutput.sku());
        assertEquals(expectedCategoryId.getValue(), actualOutput.categoryId());
        assertTrue(actualOutput.active());
        assertNotNull(actualOutput.createdAt());
        assertNotNull(actualOutput.updatedAt());

        verify(productGateway, times(1)).getById(any());
        verify(categoryGateway, times(1)).getById(any());
        verify(productGateway, times(1)).update(any());
        verifyNoMoreInteractions(productGateway);
    }

    @Test
    void givenInvalidCommand_whenCallUpdateProduct_shouldReceiveError() {
        // Given
        final var category = Category.newCategory("Electronics", "description for electronic products");

        final var expectedName = "";
        final var expectedDescription = "Updated high-performance laptop";
        final var expectedPrice = BigDecimal.valueOf(1600.00);
        final var expectedCategoryId = category.getId();

        final var product = Product.newProduct(
                "Smartphone",
                expectedDescription,
                expectedPrice,
                expectedCategoryId
        );

        final var expectedId = product.getId().getValue();

        final var aCommand = UpdateProductCommand.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedPrice,
                expectedCategoryId.getValue()
        );

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        when(productGateway.getById(any())).thenReturn(Optional.of(product));
        when(categoryGateway.getById(any())).thenReturn(Optional.of(category));

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
        final var expectedId = "PRODUCT123";
        final var expectedName = "Updated Laptop";
        final var expectedDescription = "Updated high-performance laptop";
        final var expectedPrice = BigDecimal.valueOf(1600.00);
        final var expectedCategoryId = CategoryId.unique();

        final var aCommand = UpdateProductCommand.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedPrice,
                expectedCategoryId.getValue()
        );

        final var expectedErrorMessage = "gateway error";

        doThrow(new IllegalStateException(expectedErrorMessage)).when(productGateway).getById(any());

        // When
        final var actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(aCommand)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(productGateway, times(1)).getById(any());
        verifyNoMoreInteractions(productGateway);
    }
}
