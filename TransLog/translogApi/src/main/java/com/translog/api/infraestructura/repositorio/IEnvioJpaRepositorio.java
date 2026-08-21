package com.translog.api.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.translog.api.infraestructura.persistencia.jpa.EnvioEntity;

public interface IEnvioJpaRepositorio extends JpaRepository<EnvioEntity, Integer> {
}
