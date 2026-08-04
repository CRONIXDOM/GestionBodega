package com.bodega.control.infraestructura.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.RegistroEntity;

public interface IRegistroJpaRepositorio extends JpaRepository<RegistroEntity, Integer> {

    List<RegistroEntity> findByFechaRegistro(LocalDate fechaRegistro);

    @Query("Select reg from RegistroEntity reg")
    List<RegistroEntity> listarRegistros();

    @Query("Select reg from RegistroEntity reg where reg.fechaRegistro=?1")
    List<RegistroEntity> buscarRegistroFecha(LocalDate fechaRegistro);

}