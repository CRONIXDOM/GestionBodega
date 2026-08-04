package com.bodega.control.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.TipoEntity;

public interface ITipoJpaRepositorio extends JpaRepository<TipoEntity, Integer> {

    List<TipoEntity> findByClase(String clase);

    @Query("Select tip from TipoEntity tip")
    List<TipoEntity> listarTipos();

    @Query("Select tip from TipoEntity tip where tip.descripcion=?1")
    List<TipoEntity> buscarTipoDescripcion(String descripcion);

    @Query("Select tip from TipoEntity tip where tip.clase=?1")
    List<TipoEntity> buscarTipoClase(String clase);

}