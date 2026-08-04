package com.bodega.control.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.UsuarioEntity;

public interface IUsuarioJpaRepositorio extends JpaRepository<UsuarioEntity, Integer> {

    List<UsuarioEntity> findByNombreUsuario(String nombreUsuario);

    List<UsuarioEntity> findByEstado(String estado);

    @Query("Select usu from UsuarioEntity usu")
    List<UsuarioEntity> listarUsuarios();

    @Query("Select usu from UsuarioEntity usu where usu.nombreUsuario=?1")
    List<UsuarioEntity> buscarUsuarioNombre(String nombreUsuario);

    @Query("Select usu from UsuarioEntity usu where usu.estado=?1")
    List<UsuarioEntity> buscarUsuarioEstado(String estado);

}