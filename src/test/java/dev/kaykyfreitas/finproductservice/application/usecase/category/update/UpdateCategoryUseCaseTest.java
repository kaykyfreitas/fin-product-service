package dev.kaykyfreitas.finproductservice.application.usecase.category.update;

import dev.kaykyfreitas.finproductservice.application.usecase.UseCaseTest;
import dev.kaykyfreitas.finproductservice.domain.category.Category;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryGateway;
import dev.kaykyfreitas.finproductservice.domain.exception.NotFoundException;
import dev.kaykyfreitas.finproductservice.domain.exception.NotificationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

class UpdateCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway);
    }

    @Test
    void givenValidCommand_whenCallUpdateCategory_shouldReturnUpdatedCategory() {
        // Given
        final var category = Category.newCategory("Electronics", "description for electronic products");

        final var expectedId = category.getId().getValue();
        final var expectedName = "Updated Electronics";
        final var expectedDescription = "Updated description for electronic products";

        final var aCommand = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDescription
        );

        when(categoryGateway.getById(any())).thenReturn(Optional.of(category));
        when(categoryGateway.update(any())).thenAnswer(returnsFirstArg());

        // When
        final var actualOutput = useCase.execute(aCommand);

        // Then
        assertNotNull(actualOutput);
        assertEquals(expectedId, actualOutput.id());
        assertEquals(expectedName, actualOutput.name());
        assertEquals(expectedDescription, actualOutput.description());
        assertTrue(actualOutput.active());
        assertNotNull(actualOutput.updatedAt());

        verify(categoryGateway, times(1)).getById(any());
        verify(categoryGateway, times(1)).update(any());
        verifyNoMoreInteractions(categoryGateway);
    }

    @Test
    void givenAnInvalidCommand_whenCallUpdateCategory_shouldReceiveError() {
        // Given
        final var category = Category.newCategory("Electronics", "description for electronic products");

        final var expectedId = category.getId().getValue();
        final var expectedName = "";
        final var expectedDescription = "Updated description for electronic products";

        final var aCommand = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDescription
        );

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

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

        verifyNoMoreInteractions(categoryGateway);
    }

    @Test
    void givenAValidCommand_whenGatewayThrowsRandomException_thenShouldReturnAnException() {
        // Given
        final var category = Category.newCategory("Electronics", "description for electronic products");

        final var expectedId = category.getId().getValue();
        final var expectedName = "Updated Electronics";
        final var expectedDescription = "Updated description for electronic products";

        final var aCommand = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDescription
        );

        final var expectedErrorMessage = "gateway error";

        when(categoryGateway.getById(any())).thenReturn(Optional.of(category));

        doThrow(new IllegalStateException(expectedErrorMessage)).when(categoryGateway).update(any());

        // When
        final var actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(aCommand)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(categoryGateway, times(1)).getById(any());
        verify(categoryGateway, times(1)).update(any());
        verifyNoMoreInteractions(categoryGateway);
    }

    @Test
    void givenAnInvalidCommand_whenCategoryNotExists_thenShouldReturnNotFound() {
        // Given
        final var category = Category.newCategory("Electronics", "description for electronic products");

        final var expectedId = category.getId().getValue();
        final var expectedName = "Updated Electronics";
        final var expectedDescription = "Updated description for electronic products";

        final var aCommand = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDescription
        );

        final var expectedErrorMessage = "category with id %s was not found".formatted(expectedId);

        when(categoryGateway.getById(any())).thenReturn(Optional.empty());


        // When
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(aCommand)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(categoryGateway, times(1)).getById(any());
        verifyNoMoreInteractions(categoryGateway);
    }

}
