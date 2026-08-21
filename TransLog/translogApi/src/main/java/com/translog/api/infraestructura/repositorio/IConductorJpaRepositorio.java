package com.translog.api.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.translog.api.infraestructura.persistencia.jpa.ConductorEntity;

public interface IConductorJpaRepositorio extends JpaRepository<ConductorEntity, Integer> {
}
