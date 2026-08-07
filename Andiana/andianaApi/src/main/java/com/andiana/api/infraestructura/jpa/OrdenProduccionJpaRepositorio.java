package com.andiana.api.infraestructura.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.entidad.OrdenProduccionEntidad;

public interface OrdenProduccionJpaRepositorio extends JpaRepository<OrdenProduccionEntidad, Integer> {

}
