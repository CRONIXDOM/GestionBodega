package com.andiana.api.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.persistencia.jpa.ProductoEntity;

public interface IProductoJpaRepositorio extends JpaRepository<ProductoEntity, Integer> {
}
