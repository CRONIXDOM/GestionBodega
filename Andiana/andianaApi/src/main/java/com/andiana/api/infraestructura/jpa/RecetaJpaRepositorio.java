package com.andiana.api.infraestructura.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.entidad.RecetaEntidad;

public interface RecetaJpaRepositorio extends JpaRepository<RecetaEntidad, Integer> {

	List<RecetaEntidad> findByIdProductoOrderByFechaAsc(Integer idProducto);
}
