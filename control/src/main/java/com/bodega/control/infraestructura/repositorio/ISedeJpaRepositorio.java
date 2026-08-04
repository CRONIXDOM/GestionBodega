package com.bodega.control.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bodega.control.infraestructura.persistencia.jpa.SedeEntity;

public interface ISedeJpaRepositorio extends JpaRepository<SedeEntity, Integer> {

}
