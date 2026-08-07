package com.andiana.api.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.persistencia.jpa.DetalleRecetaEntity;

public interface IDetalleRecetaJpaRepositorio extends JpaRepository<DetalleRecetaEntity, Integer> {

	List<DetalleRecetaEntity> findByIdReceta(int idReceta);
}
