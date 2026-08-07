package com.andiana.api.infraestructura.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andiana.api.infraestructura.entidad.MateriaPrimaEntidad;

public interface MateriaPrimaJpaRepositorio extends JpaRepository<MateriaPrimaEntidad, Integer> {

}
