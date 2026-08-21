package com.translog.api.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.translog.api.infraestructura.persistencia.jpa.CiudadEntity;

public interface ICiudadJpaRepositorio extends JpaRepository<CiudadEntity, Integer> {
}
