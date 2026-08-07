package com.andiana.api.infraestructura.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.entidad.LoteProduccionEntidad;

public interface LoteProduccionJpaRepositorio extends JpaRepository<LoteProduccionEntidad, Integer> {

	List<LoteProduccionEntidad> findByIdOrden(Integer idOrden);
}
