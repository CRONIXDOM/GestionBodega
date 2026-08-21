package com.translog.api.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.translog.api.infraestructura.persistencia.jpa.VehiculoEntity;

public interface IVehiculoJpaRepositorio extends JpaRepository<VehiculoEntity, Integer> {
}
