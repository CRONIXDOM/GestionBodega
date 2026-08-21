package com.translog.api.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.translog.api.infraestructura.persistencia.jpa.DespachoEntity;

public interface IDespachoJpaRepositorio extends JpaRepository<DespachoEntity, Integer> {
}
