package com.andiana.api.infraestructura.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.entidad.InventarioProductoEntidad;

public interface InventarioProductoJpaRepositorio extends JpaRepository<InventarioProductoEntidad, Integer> {

	List<InventarioProductoEntidad> findByIdLote(Integer idLote);
}
