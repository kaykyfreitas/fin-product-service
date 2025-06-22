package dev.kaykyfreitas.finproductservice.domain.product;

import dev.kaykyfreitas.finproductservice.domain.Identifier;
import dev.kaykyfreitas.finproductservice.domain.utils.IdUtils;

import java.util.Objects;

public class ProductId extends Identifier {

    private final String id;

    private ProductId(final String id) {
        this.id = id;
    }

    public static ProductId from(final String id) {
        return new ProductId(id);
    }

    public static ProductId unique() {
        return new ProductId(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return this.id;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final ProductId productId = (ProductId) o;
        return Objects.equals(id, productId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
