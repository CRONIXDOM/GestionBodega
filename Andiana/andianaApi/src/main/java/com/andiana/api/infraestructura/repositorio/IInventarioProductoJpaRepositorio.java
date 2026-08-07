package com.andiana.api.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.persistencia.jpa.InventarioProductoEntity;

public interface IInventarioProductoJpaRepositorio extends JpaRepository<InventarioProductoEntity, Integer> {

	List<InventarioProductoEntity> findByIdLote(int idLote);
}
