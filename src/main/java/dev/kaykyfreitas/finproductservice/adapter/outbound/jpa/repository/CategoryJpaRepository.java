package dev.kaykyfreitas.finproductservice.adapter.outbound.jpa.repository;

import dev.kaykyfreitas.finproductservice.adapter.outbound.jpa.entity.CategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryJpaEntity, String> {
}
