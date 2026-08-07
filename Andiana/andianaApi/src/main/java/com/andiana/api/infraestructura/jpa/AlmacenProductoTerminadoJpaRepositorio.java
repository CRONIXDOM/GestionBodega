package com.andiana.api.infraestructura.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.entidad.AlmacenProductoTerminadoEntidad;

public interface AlmacenProductoTerminadoJpaRepositorio extends JpaRepository<AlmacenProductoTerminadoEntidad, Integer> {

	Optional<AlmacenProductoTerminadoEntidad> findByIdLote(Integer idLote);
}
