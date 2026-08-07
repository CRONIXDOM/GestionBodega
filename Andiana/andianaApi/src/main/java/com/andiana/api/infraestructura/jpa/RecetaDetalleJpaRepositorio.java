package com.andiana.api.infraestructura.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.entidad.RecetaDetalleEntidad;

public interface RecetaDetalleJpaRepositorio extends JpaRepository<RecetaDetalleEntidad, Integer> {

	List<RecetaDetalleEntidad> findByIdReceta(Integer idReceta);
}
