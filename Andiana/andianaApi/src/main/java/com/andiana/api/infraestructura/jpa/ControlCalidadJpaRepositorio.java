package com.andiana.api.infraestructura.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.entidad.ControlCalidadEntidad;

public interface ControlCalidadJpaRepositorio extends JpaRepository<ControlCalidadEntidad, Integer> {

	List<ControlCalidadEntidad> findByIdLoteOrderByFechaControlDescIdControlDesc(Integer idLote);
}
