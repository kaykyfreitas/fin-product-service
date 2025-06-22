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
        String validatedName = productName;
        if (productName == null || productName.isBlank())
            validatedName = "";

        final String sanitizedName = sanitizeName(validatedName);
        final String paddedName = padName(sanitizedName);
        final String truncatedName = truncateName(paddedName);
        final String timestamp = generateTimestamp();
        final String sku = generateSku(truncatedName, timestamp);
        return new ProductSku(sku);
    }

    private static String sanitizeName(final String productName) {
        return productName.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
    }

    private static String padName(final String name) {
        return name.length() < 7 ? String.format("%-7s", name).replace(' ', 'X') : name;
    }

    private static String truncateName(final String name) {
        return name.substring(0, Math.min(name.length(), 7));
    }

    private static String generateTimestamp() {
        return String.valueOf(Instant.now().toEpochMilli());
    }

    private static String generateSku(final String name, final String timestamp) {
        return (name + timestamp).substring(0, 20);
    }

    public String getValue() {
        return value;
    }

}
