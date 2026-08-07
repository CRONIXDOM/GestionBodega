package com.andiana.api.infraestructura.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.entidad.DetalleRecetaEntidad;

public interface DetalleRecetaJpaRepositorio extends JpaRepository<DetalleRecetaEntidad, Integer> {

	List<DetalleRecetaEntidad> findByIdReceta(Integer idReceta);
}
