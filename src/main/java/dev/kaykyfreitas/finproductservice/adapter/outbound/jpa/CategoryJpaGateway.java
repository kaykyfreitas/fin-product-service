package dev.kaykyfreitas.finproductservice.adapter.outbound.jpa;

import dev.kaykyfreitas.finproductservice.adapter.outbound.jpa.entity.CategoryJpaEntity;
import dev.kaykyfreitas.finproductservice.adapter.outbound.jpa.mapper.CategoryJpaMapper;
import dev.kaykyfreitas.finproductservice.adapter.outbound.jpa.repository.CategoryJpaRepository;
import dev.kaykyfreitas.finproductservice.domain.category.Category;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryGateway;
import dev.kaykyfreitas.finproductservice.domain.category.CategoryId;
import dev.kaykyfreitas.finproductservice.domain.pagination.Pagination;
import dev.kaykyfreitas.finproductservice.domain.pagination.SearchQuery;
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
public class CategoryJpaGateway implements CategoryGateway {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Category create(final Category category) {
        return save(category);
    }

    @Override
    public Optional<Category> getById(final CategoryId id) {
        return categoryJpaRepository.findById(id.getValue()).map(CategoryJpaMapper::fromJpa);
    }

    @Override
    public Pagination<Category> getAll(final SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.Direction.fromString(query.direction()),
                query.sort()
        );

        if (isNull(query.terms())) {
            final var pageResult = categoryJpaRepository.findAll(page);

            return new Pagination<>(
                    pageResult.getNumber(),
                    pageResult.getSize(),
                    pageResult.getTotalElements(),
                    pageResult.map(CategoryJpaMapper::fromJpa).toList()
            );
        }

        final var example = assembleExample(query.terms());

        final var pageResult = this.categoryJpaRepository.findAll(example, page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CategoryJpaMapper::fromJpa).toList()
        );
    }

    @Override
    public Category update(final Category category) {
        return save(category);
    }

    @Override
    public void delete(final CategoryId id) {
        if (categoryJpaRepository.existsById(id.getValue()))
            categoryJpaRepository.deleteById(id.getValue());
    }

    private Category save(final Category category) {
        return CategoryJpaMapper.fromJpa(categoryJpaRepository.save(CategoryJpaMapper.toJpa(category)));
    }

    private Example<CategoryJpaEntity> assembleExample(final String terms) {
        final var categoryExample = new CategoryJpaEntity();
        categoryExample.setName(terms);
        categoryExample.setDescription(terms);

        final var matcher = ExampleMatcher.matchingAny()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        return Example.of(categoryExample, matcher);
    }

}
