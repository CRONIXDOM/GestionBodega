package com.andiana.api.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.persistencia.jpa.LoteProduccionEntity;

public interface ILoteProduccionJpaRepositorio extends JpaRepository<LoteProduccionEntity, Integer> {

	List<LoteProduccionEntity> findByIdOrden(int idOrden);
}
