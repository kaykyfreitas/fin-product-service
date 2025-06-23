package dev.kaykyfreitas.finproductservice.adapter.outbound.jpa.repository;

import dev.kaykyfreitas.finproductservice.adapter.outbound.jpa.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, String> {
}
