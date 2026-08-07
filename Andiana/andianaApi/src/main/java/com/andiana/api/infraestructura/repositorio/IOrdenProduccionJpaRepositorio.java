package com.andiana.api.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.persistencia.jpa.OrdenProduccionEntity;

public interface IOrdenProduccionJpaRepositorio extends JpaRepository<OrdenProduccionEntity, Integer> {
}
