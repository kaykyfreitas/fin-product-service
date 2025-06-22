package dev.kaykyfreitas.finproductservice.application.usecase.category.delete;

import dev.kaykyfreitas.finproductservice.application.usecase.UseCaseTest;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryGateway;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway);
    }

    @Test
    void givenValidId_whenCallDeleteCategory_shouldDeleteCategory() {
        // Given
        final var expectedId = CategoryId.unique();

        doNothing().when(categoryGateway).delete(expectedId);

        // When
        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // Then
        verify(categoryGateway, times(1)).delete(expectedId);
        verifyNoMoreInteractions(categoryGateway);
    }

    @Test
    void givenValidId_whenGatewayThrowsRandomException_thenShouldThrowException() {
        // Given
        final var expectedId = CategoryId.unique();
        final var expectedErrorMessage = "gateway error";

        doThrow(new IllegalStateException(expectedErrorMessage)).when(categoryGateway).delete(expectedId);

        // When
        final var actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(categoryGateway, times(1)).delete(expectedId);
        verifyNoMoreInteractions(categoryGateway);
    }
}
