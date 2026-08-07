package com.andiana.api.infraestructura.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.entidad.RecetaProduccionEntidad;

public interface RecetaProduccionJpaRepositorio extends JpaRepository<RecetaProduccionEntidad, Integer> {

	List<RecetaProduccionEntidad> findByIdProductoOrderByVersionAsc(Integer idProducto);
}
