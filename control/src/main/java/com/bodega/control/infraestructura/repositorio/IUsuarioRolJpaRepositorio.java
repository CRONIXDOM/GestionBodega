package com.bodega.control.infraestructura.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.UsuarioRolEntity;

public interface IUsuarioRolJpaRepositorio extends JpaRepository<UsuarioRolEntity, Integer> {

    List<UsuarioRolEntity> findByFechaAsignacion(LocalDate fechaAsignacion);

    @Query("Select ur from UsuarioRolEntity ur")
    List<UsuarioRolEntity> listarUsuariosRoles();

    @Query("Select ur from UsuarioRolEntity ur where ur.fechaAsignacion=?1")
    List<UsuarioRolEntity> buscarUsuarioRolFecha(LocalDate fechaAsignacion);

}