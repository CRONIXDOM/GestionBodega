package com.andiana.api.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.persistencia.jpa.RecetaProduccionEntity;

public interface IRecetaProduccionJpaRepositorio extends JpaRepository<RecetaProduccionEntity, Integer> {

	List<RecetaProduccionEntity> findByIdProductoOrderByVersionAsc(int idProducto);
}
