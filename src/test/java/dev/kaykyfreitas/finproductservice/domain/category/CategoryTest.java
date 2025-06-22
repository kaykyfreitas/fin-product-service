package dev.kaykyfreitas.finproductservice.domain.category;

import dev.kaykyfreitas.finproductservice.domain.exception.NotificationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void givenValidParams_whenCreateNewCategory_thenReturnCategory() {
        final var expectedName = "Electronics";
        final var expectedDescription = "Category for electronic products";
        
        final var actualCategory = Category.newCategory(
                expectedName,
                expectedDescription
        );
        
        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertTrue(actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
    }

    @Test
    void givenInvalidNullName_whenCallNewCategory_thenShouldReceiveError() {
        final String expectedName = null;
        final var expectedDescription = "Category for electronic products";

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualException = assertThrows(
                NotificationException.class,
                () -> Category.newCategory(
                        expectedName,
                        expectedDescription
                )
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidEmptyName_whenCallNewCategory_thenShouldReceiveError() {
        final String expectedName = "  ";
        final var expectedDescription = "Category for electronic products";

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualException = assertThrows(
                NotificationException.class,
                () -> Category.newCategory(
                        expectedName,
                        expectedDescription
                )
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNameLengthLessThan3_whenCallNewCategory_thenShouldReceiveError() {
        final String expectedName = "El";
        final var expectedDescription = "Category for electronic products";

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 100 characters";

        final var actualException = assertThrows(
                NotificationException.class,
                () -> Category.newCategory(
                        expectedName,
                        expectedDescription
                )
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNameLengthMoreThan100_whenCallNewCategory_thenShouldReceiveError() {
        final String expectedName = "E".repeat(101);
        final var expectedDescription = "Category for electronic products";

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 100 characters";

        final var actualException = assertThrows(
                NotificationException.class,
                () -> Category.newCategory(
                        expectedName,
                        expectedDescription
                )
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNullDescription_whenCallNewCategory_thenShouldReceiveError() {
        final var expectedName = "Electronics";
        final String expectedDescription = null;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'description' should not be null";

        final var actualException = assertThrows(
                NotificationException.class,
                () -> Category.newCategory(
                        expectedName,
                        expectedDescription
                )
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }
    
}
