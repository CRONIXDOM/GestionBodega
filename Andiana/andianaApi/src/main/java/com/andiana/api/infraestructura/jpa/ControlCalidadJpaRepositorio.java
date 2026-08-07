package com.andiana.api.infraestructura.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.entidad.ControlCalidadEntidad;

public interface ControlCalidadJpaRepositorio extends JpaRepository<ControlCalidadEntidad, Integer> {

	Optional<ControlCalidadEntidad> findByIdLote(Integer idLote);
}
