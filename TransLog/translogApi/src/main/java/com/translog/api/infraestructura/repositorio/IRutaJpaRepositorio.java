package com.translog.api.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.translog.api.infraestructura.persistencia.jpa.RutaEntity;

public interface IRutaJpaRepositorio extends JpaRepository<RutaEntity, Integer> {
}
