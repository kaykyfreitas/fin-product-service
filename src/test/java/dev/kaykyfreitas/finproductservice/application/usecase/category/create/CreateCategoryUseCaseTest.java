package dev.kaykyfreitas.finproductservice.application.usecase.category.create;

import dev.kaykyfreitas.finproductservice.application.usecase.UseCaseTest;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryGateway;
import dev.kaykyfreitas.finproductservice.domain.exception.NotificationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

class CreateCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway);
    }

    @Test
    void givenValidCommand_whenCallCreateCategory_shouldReturnCategory() {
        // Given
        final var expectedName = "Electronics";
        final var expectedDescription = "Category for electronic products";

        final var aCommand = CreateCategoryCommand.with(
                expectedName,
                expectedDescription
        );

        when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

        // When
        final var actualOutput = useCase.execute(aCommand);

        // Then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());
        assertEquals(expectedName, actualOutput.name());
        assertEquals(expectedDescription, actualOutput.description());
        assertTrue(actualOutput.active());
        assertNotNull(actualOutput.createdAt());
        assertNotNull(actualOutput.updatedAt());
        assertEquals(actualOutput.createdAt(), actualOutput.updatedAt());
        assertNull(actualOutput.deletedAt());

        verify(categoryGateway, times(1)).create(any());
        verifyNoMoreInteractions(categoryGateway);
    }

    @Test
    void givenAnInvalidCommand_whenCallCreateCategory_shouldReceiveError() {
        // Given
        final var expectedName = "";
        final var expectedDescription = "Category for electronic products";

        final var aCommand = CreateCategoryCommand.with(
                expectedName,
                expectedDescription
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

        verifyNoMoreInteractions(categoryGateway);
    }

    @Test
    void givenAValidCommand_whenGatewayThrowsRandomException_thenShouldReturnAnException() {
        // Given
        final var expectedName = "Electronics";
        final var expectedDescription = "Category for electronic products";

        final var aCommand = CreateCategoryCommand.with(
                expectedName,
                expectedDescription
        );

        final var expectedErrorMessage = "gateway error";

        doThrow(new IllegalStateException(expectedErrorMessage)).when(categoryGateway).create(any());

        // When
        final var actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(aCommand)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(categoryGateway, times(1)).create(any());
        verifyNoMoreInteractions(categoryGateway);
    }
    
}
