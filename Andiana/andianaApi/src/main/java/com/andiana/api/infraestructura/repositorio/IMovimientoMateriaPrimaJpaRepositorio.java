package com.andiana.api.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.persistencia.jpa.MovimientoMateriaPrimaEntity;

public interface IMovimientoMateriaPrimaJpaRepositorio extends JpaRepository<MovimientoMateriaPrimaEntity, Integer> {

	List<MovimientoMateriaPrimaEntity> findByIdMateriaOrderByFechaAscIdMovimientoAsc(int idMateria);
}
