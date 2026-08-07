package com.andiana.api.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.persistencia.jpa.MateriaPrimaEntity;

public interface IMateriaPrimaJpaRepositorio extends JpaRepository<MateriaPrimaEntity, Integer> {
}
