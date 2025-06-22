package dev.kaykyfreitas.finproductservice.domain.category;

import dev.kaykyfreitas.finproductservice.domain.Identifier;
import dev.kaykyfreitas.finproductservice.domain.utils.IdUtils;

import java.util.Objects;

public class CategoryId extends Identifier {

    private final String id;

    private CategoryId(final String id) {
        this.id = id;
    }

    public static CategoryId from(final String id) {
        return new CategoryId(id);
    }

    public static CategoryId unique() {
        return new CategoryId(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return this.id;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final CategoryId categoryId = (CategoryId) o;
        return Objects.equals(id, categoryId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
