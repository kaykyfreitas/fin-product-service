package dev.kaykyfreitas.finproductservice.application.usecase.product.delete;

import dev.kaykyfreitas.finproductservice.application.usecase.UseCaseTest;
import dev.kaykyfreitas.finproductservice.domain.product.ProductGateway;
import dev.kaykyfreitas.finproductservice.domain.product.ProductId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteProductUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteProductUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway);
    }

    @Test
    void givenValidId_whenCallDeleteProduct_shouldDeleteProduct() {
        // Given
        final var expectedId = ProductId.unique();

        doNothing().when(productGateway).delete(expectedId);

        // When
        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // Then
        verify(productGateway, times(1)).delete(expectedId);
        verifyNoMoreInteractions(productGateway);
    }


    @Test
    void givenValidId_whenGatewayThrowsRandomException_thenShouldThrowException() {
        // Given
        final var expectedId = ProductId.unique();
        final var expectedErrorMessage = "gateway error";

        doThrow(new IllegalStateException(expectedErrorMessage)).when(productGateway).delete(expectedId);

        // When
        final var actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(productGateway, times(1)).delete(expectedId);
        verifyNoMoreInteractions(productGateway);
    }
}
