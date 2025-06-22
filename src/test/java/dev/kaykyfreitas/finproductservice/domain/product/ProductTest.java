package dev.kaykyfreitas.finproductservice.domain.product;

import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.exception.NotificationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void givenValidParams_whenCreateNewProduct_thenReturnProduct() {
        final var expectedName = "Laptop";
        final var expectedDescription = "High-performance laptop";
        final var expectedPrice = BigDecimal.valueOf(1500.00);
        final var expectedSku = ProductSku.generate("LAPTOP123");
        final var expectedCategoryId = CategoryId.unique();

        final var actualProduct = Product.newProduct(
                expectedName,
                expectedDescription,
                expectedPrice,
                expectedSku,
                expectedCategoryId
        );

        assertNotNull(actualProduct);
        assertNotNull(actualProduct.getId());
        assertEquals(expectedName, actualProduct.getName());
        assertEquals(expectedDescription, actualProduct.getDescription());
        assertEquals(expectedPrice, actualProduct.getPrice());
        assertEquals(expectedSku, actualProduct.getSku());
        assertEquals(expectedCategoryId, actualProduct.getCategoryId());
        assertTrue(actualProduct.isActive());
        assertNotNull(actualProduct.getCreatedAt());
        assertNotNull(actualProduct.getUpdatedAt());
        assertNull(actualProduct.getDeletedAt());
    }

    @Test
    void givenInvalidNullName_whenCallNewProduct_thenShouldReceiveError() {
        final String expectedName = null;
        final var expectedDescription = "High-performance laptop";
        final var expectedPrice = BigDecimal.valueOf(1500.00);
        final var expectedSku = ProductSku.generate("LAPTOP123");
        final var expectedCategoryId = CategoryId.unique();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualException = assertThrows(
                NotificationException.class,
                () -> Product.newProduct(
                        expectedName,
                        expectedDescription,
                        expectedPrice,
                        expectedSku,
                        expectedCategoryId
                )
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNegativePrice_whenCallNewProduct_thenShouldReceiveError() {
        final var expectedName = "Laptop";
        final var expectedDescription = "High-performance laptop";
        final var expectedPrice = BigDecimal.valueOf(-1500.00);
        final var expectedSku = ProductSku.generate("LAPTOP123");
        final var expectedCategoryId = CategoryId.unique();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'price' must be greater than zero";

        final var actualException = assertThrows(
                NotificationException.class,
                () -> Product.newProduct(
                        expectedName,
                        expectedDescription,
                        expectedPrice,
                        expectedSku,
                        expectedCategoryId
                )
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNullSku_whenCallNewProduct_thenShouldReceiveError() {
        final var expectedName = "Laptop";
        final var expectedDescription = "High-performance laptop";
        final var expectedPrice = BigDecimal.valueOf(1500.00);
        final ProductSku expectedSku = null;
        final var expectedCategoryId = CategoryId.unique();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'sku' should not be null";

        final var actualException = assertThrows(
                NotificationException.class,
                () -> Product.newProduct(
                        expectedName,
                        expectedDescription,
                        expectedPrice,
                        expectedSku,
                        expectedCategoryId
                )
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNullCategoryId_whenCallNewProduct_thenShouldReceiveError() {
        final var expectedName = "Laptop";
        final var expectedDescription = "High-performance laptop";
        final var expectedPrice = BigDecimal.valueOf(1500.00);
        final var expectedSku = ProductSku.generate("LAPTOP123");
        final CategoryId expectedCategoryId = null;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'categoryId' should not be null";

        final var actualException = assertThrows(
                NotificationException.class,
                () -> Product.newProduct(
                        expectedName,
                        expectedDescription,
                        expectedPrice,
                        expectedSku,
                        expectedCategoryId
                )
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidEmptyName_whenCallNewProduct_thenShouldReceiveError() {
        final String expectedName = "  ";
        final var expectedDescription = "High-performance laptop";
        final var expectedPrice = BigDecimal.valueOf(1500.00);
        final var expectedSku = ProductSku.generate("LAPTOP123");
        final var expectedCategoryId = CategoryId.unique();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualException = assertThrows(
                NotificationException.class,
                () -> Product.newProduct(
                        expectedName,
                        expectedDescription,
                        expectedPrice,
                        expectedSku,
                        expectedCategoryId
                )
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNameLengthLessThan3_whenCallNewProduct_thenShouldReceiveError() {
        final String expectedName = "La";
        final var expectedDescription = "High-performance laptop";
        final var expectedPrice = BigDecimal.valueOf(1500.00);
        final var expectedSku = ProductSku.generate("LAPTOP123");
        final var expectedCategoryId = CategoryId.unique();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 100 characters";

        final var actualException = assertThrows(
                NotificationException.class,
                () -> Product.newProduct(
                        expectedName,
                        expectedDescription,
                        expectedPrice,
                        expectedSku,
                        expectedCategoryId
                )
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNameLengthMoreThan100_whenCallNewProduct_thenShouldReceiveError() {
        final String expectedName = "L".repeat(101);
        final var expectedDescription = "High-performance laptop";
        final var expectedPrice = BigDecimal.valueOf(1500.00);
        final var expectedSku = ProductSku.generate("LAPTOP123");
        final var expectedCategoryId = CategoryId.unique();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 100 characters";

        final var actualException = assertThrows(
                NotificationException.class,
                () -> Product.newProduct(
                        expectedName,
                        expectedDescription,
                        expectedPrice,
                        expectedSku,
                        expectedCategoryId
                )
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidEmptyDescription_whenCallNewProduct_thenShouldReceiveError() {
        final var expectedName = "Laptop";
        final String expectedDescription = "  ";
        final var expectedPrice = BigDecimal.valueOf(1500.00);
        final var expectedSku = ProductSku.generate("LAPTOP123");
        final var expectedCategoryId = CategoryId.unique();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'description' should not be empty";

        final var actualException = assertThrows(
                NotificationException.class,
                () -> Product.newProduct(
                        expectedName,
                        expectedDescription,
                        expectedPrice,
                        expectedSku,
                        expectedCategoryId
                )
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidDescriptionLengthLessThan3_whenCallNewProduct_thenShouldReceiveError() {
        final var expectedName = "Laptop";
        final String expectedDescription = "Hi";
        final var expectedPrice = BigDecimal.valueOf(1500.00);
        final var expectedSku = ProductSku.generate("LAPTOP123");
        final var expectedCategoryId = CategoryId.unique();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'description' must be between 5 and 255 characters";

        final var actualException = assertThrows(
                NotificationException.class,
                () -> Product.newProduct(
                        expectedName,
                        expectedDescription,
                        expectedPrice,
                        expectedSku,
                        expectedCategoryId
                )
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidDescriptionLengthMoreThan255_whenCallNewProduct_thenShouldReceiveError() {
        final var expectedName = "Laptop";
        final String expectedDescription = "A".repeat(256);
        final var expectedPrice = BigDecimal.valueOf(1500.00);
        final var expectedSku = ProductSku.generate("LAPTOP123");
        final var expectedCategoryId = CategoryId.unique();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'description' must be between 5 and 255 characters";

        final var actualException = assertThrows(
                NotificationException.class,
                () -> Product.newProduct(
                        expectedName,
                        expectedDescription,
                        expectedPrice,
                        expectedSku,
                        expectedCategoryId
                )
        );

        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }
}
