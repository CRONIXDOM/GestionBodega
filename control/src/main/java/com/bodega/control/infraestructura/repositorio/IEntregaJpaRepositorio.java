package com.bodega.control.infraestructura.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.EntregaEntity;

public interface IEntregaJpaRepositorio extends JpaRepository<EntregaEntity, Integer> {

    List<EntregaEntity> findByResponsableEntrega(String responsableEntrega);

    @Query("Select ent from EntregaEntity ent")
    List<EntregaEntity> listarEntregas();

    @Query("Select ent from EntregaEntity ent where ent.responsableEntrega=?1")
    List<EntregaEntity> buscarEntregaResponsable(String responsableEntrega);

    @Query("Select ent from EntregaEntity ent where ent.fechaEntrega=?1")
    List<EntregaEntity> buscarEntregaFecha(LocalDate fechaEntrega);

}
