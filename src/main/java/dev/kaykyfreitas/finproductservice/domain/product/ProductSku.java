package dev.kaykyfreitas.finproductservice.domain.product;

import dev.kaykyfreitas.finproductservice.domain.ValueObject;

import java.time.Instant;

public class ProductSku extends ValueObject {

    private final String value;

    private ProductSku(final String value) {
        this.value = value;
    }

    public static ProductSku from(final String value) {
        return new ProductSku(value);
    }

    public static ProductSku generate(final String productName) {
        final String sanitizedName = productName.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        final String truncatedName = sanitizedName.length() > 7 ? sanitizedName.substring(0, 7) : sanitizedName;
        final String timestamp = String.valueOf(Instant.now().toEpochMilli());
        final String sku = (truncatedName + timestamp).substring(0, 20);
        return new ProductSku(sku);
    }

    public String getValue() {
        return value;
    }

}
