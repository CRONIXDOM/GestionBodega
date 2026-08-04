package com.bodega.control.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.RolEntity;

public interface IRolJpaRepositorio extends JpaRepository<RolEntity, Integer> {

    List<RolEntity> findByNombreRol(String nombreRol);

    @Query("Select rol from RolEntity rol")
    List<RolEntity> listarRoles();

    @Query("Select rol from RolEntity rol where rol.nombreRol=?1")
    List<RolEntity> buscarRolNombre(String nombreRol);

    @Query("Select rol from RolEntity rol where rol.descripcionRol=?1")
    List<RolEntity> buscarRolDescripcion(String descripcionRol);

}