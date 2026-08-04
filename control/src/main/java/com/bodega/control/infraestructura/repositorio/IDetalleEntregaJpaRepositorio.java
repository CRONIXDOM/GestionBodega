package com.bodega.control.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.DetalleEntregaEntity;

public interface IDetalleEntregaJpaRepositorio extends JpaRepository<DetalleEntregaEntity, Integer> {

    List<DetalleEntregaEntity> findByNombreProducto(String nombreProducto);

    @Query("Select det from DetalleEntregaEntity det")
    List<DetalleEntregaEntity> listarDetalleEntrega();

    @Query("Select det from DetalleEntregaEntity det where det.codigoEvento=?1")
    List<DetalleEntregaEntity> buscarDetalleEntregaCodigo(String codigoEvento);

}