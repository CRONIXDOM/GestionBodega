package com.andiana.api.infraestructura.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.entidad.MovimientoInventarioEntidad;

public interface MovimientoInventarioJpaRepositorio extends JpaRepository<MovimientoInventarioEntidad, Integer> {

	List<MovimientoInventarioEntidad> findByIdMateriaPrimaOrderByFechaAscIdMovimientoAsc(Integer idMateriaPrima);
}
