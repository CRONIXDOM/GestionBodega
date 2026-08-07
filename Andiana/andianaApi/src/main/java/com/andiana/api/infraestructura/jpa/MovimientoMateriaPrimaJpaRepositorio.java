package com.andiana.api.infraestructura.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.entidad.MovimientoMateriaPrimaEntidad;

public interface MovimientoMateriaPrimaJpaRepositorio extends JpaRepository<MovimientoMateriaPrimaEntidad, Integer> {

	List<MovimientoMateriaPrimaEntidad> findByIdMateriaOrderByFechaAscIdMovimientoAsc(Integer idMateria);
}
