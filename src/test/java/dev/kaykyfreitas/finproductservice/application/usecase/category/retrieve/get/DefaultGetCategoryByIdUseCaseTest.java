package dev.kaykyfreitas.finproductservice.application.usecase.category.retrieve.get;

import dev.kaykyfreitas.finproductservice.application.usecase.UseCaseTest;
import dev.kaykyfreitas.finproductservice.domain.category.Category;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryGateway;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.exception.DomainException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultGetCategoryByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetCategoryByIdUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway);
    }

    @Test
    void givenValidId_whenCallGetCategoryById_shouldReturnCategory() {
        // Given
        final var expectedId = CategoryId.unique();
        final var expectedName = "Electronics";
        final var expectedDescription = "Category for electronic products";
        final var expectedCreatedAt = Instant.now();
        final var expectedUpdatedAt = Instant.now();

        final var aCategory = new Category(
                expectedId,
                expectedName,
                expectedDescription,
                true,
                expectedCreatedAt,
                expectedUpdatedAt,
                null
        );

        when(categoryGateway.getById(expectedId)).thenReturn(Optional.of(aCategory));

        // When
        final var actualOutput = useCase.execute(expectedId.getValue());

        // Then
        assertNotNull(actualOutput);
        assertEquals(expectedId.getValue(), actualOutput.id());
        assertEquals(expectedName, actualOutput.name());
        assertEquals(expectedDescription, actualOutput.description());
        assertTrue(actualOutput.active());
        assertEquals(expectedCreatedAt, actualOutput.createdAt());
        assertEquals(expectedUpdatedAt, actualOutput.updatedAt());
        assertNull(actualOutput.deletedAt());

        verify(categoryGateway, times(1)).getById(expectedId);
        verifyNoMoreInteractions(categoryGateway);
    }

    @Test
    void givenInvalidId_whenCallGetCategoryById_shouldThrowDomainException() {
        // Given
        final var expectedId = CategoryId.unique();
        final var expectedErrorMessage = "category with id %s was not found".formatted(expectedId.getValue());

        when(categoryGateway.getById(expectedId)).thenReturn(Optional.empty());

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(categoryGateway, times(1)).getById(expectedId);
        verifyNoMoreInteractions(categoryGateway);
    }

    @Test
    void givenValidId_whenGatewayThrowsRandomException_thenShouldThrowException() {
        // Given
        final var expectedId = CategoryId.unique();
        final var expectedErrorMessage = "gateway error";

        doThrow(new IllegalStateException(expectedErrorMessage)).when(categoryGateway).getById(expectedId);

        // When
        final var actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(categoryGateway, times(1)).getById(expectedId);
        verifyNoMoreInteractions(categoryGateway);
    }
}
