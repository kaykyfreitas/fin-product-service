package dev.kaykyfreitas.finproductservice.adapter.outbound.jpa;

import dev.kaykyfreitas.finproductservice.adapter.outbound.jpa.entity.ProductJpaEntity;
import dev.kaykyfreitas.finproductservice.adapter.outbound.jpa.mapper.ProductJpaMapper;
import dev.kaykyfreitas.finproductservice.adapter.outbound.jpa.repository.ProductJpaRepository;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.pagination.Pagination;
import dev.kaykyfreitas.finproductservice.domain.pagination.SearchQuery;
import dev.kaykyfreitas.finproductservice.domain.product.Product;
import dev.kaykyfreitas.finproductservice.domain.product.ProductGateway;
import dev.kaykyfreitas.finproductservice.domain.product.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class ProductJpaGateway implements ProductGateway {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product create(final Product product) {
        return save(product);
    }

    @Override
    public Optional<Product> getById(final ProductId id) {
        return productJpaRepository.findById(id.getValue()).map(ProductJpaMapper::fromJpa);
    }

    @Override
    public Pagination<Product> getAll(final SearchQuery searchQuery) {
        return getAll(searchQuery, Optional.empty());
    }

    @Override
    public Pagination<Product> getAll(final CategoryId categoryId, final SearchQuery searchQuery) {
        return getAll(searchQuery, Optional.of(categoryId));
    }

    @Override
    public Product update(final Product product) {
        return save(product);
    }

    @Override
    public void delete(final ProductId id) {
        if (productJpaRepository.existsById(id.getValue()))
            productJpaRepository.deleteById(id.getValue());
    }

    private Product save(final Product product) {
        return ProductJpaMapper.fromJpa(productJpaRepository.save(ProductJpaMapper.toJpa(product)));
    }

    public Pagination<Product> getAll(final SearchQuery query, final Optional<CategoryId> categoryId) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.Direction.fromString(query.direction()),
                query.sort()
        );

        if (isNull(query.terms()) && categoryId.isEmpty()) {
            final var pageResult = productJpaRepository.findAll(page);

            return new Pagination<>(
                    pageResult.getNumber(),
                    pageResult.getSize(),
                    pageResult.getTotalElements(),
                    pageResult.map(ProductJpaMapper::fromJpa).toList()
            );
        }

        final var example = assembleExample(query.terms(), categoryId);

        final var pageResult = this.productJpaRepository.findAll(example, page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(ProductJpaMapper::fromJpa).toList()
        );
    }

    private Example<ProductJpaEntity> assembleExample(final String terms, final Optional<CategoryId> categoryId) {
        final ProductJpaEntity productExample = new ProductJpaEntity();

        if (terms != null) {
            productExample.setName(terms);
            productExample.setDescription(terms);
            productExample.setSku(terms);
        }

        categoryId.ifPresent(id -> productExample.setCategoryId(id.getValue()));

        final var matcher = ExampleMatcher.matchingAny()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        return Example.of(productExample, matcher);
    }

}
