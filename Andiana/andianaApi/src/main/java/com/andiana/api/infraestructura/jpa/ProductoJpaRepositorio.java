package com.andiana.api.infraestructura.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.entidad.ProductoEntidad;

public interface ProductoJpaRepositorio extends JpaRepository<ProductoEntidad, Integer> {

}
