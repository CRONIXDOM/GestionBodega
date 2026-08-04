package com.bodega.control.infraestructura.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.UbicacionEntity;

public interface IUbicacionJpaRepositorio extends JpaRepository<UbicacionEntity, Integer> {

    List<UbicacionEntity> findByCantidadUbicacion(String cantidadUbicacion);

    List<UbicacionEntity> findByFechaUbicacion(LocalDate fechaUbicacion);

    @Query("Select ubi from UbicacionEntity ubi")
    List<UbicacionEntity> listarUbicaciones();

    @Query("Select ubi from UbicacionEntity ubi where ubi.cantidadUbicacion=?1")
    List<UbicacionEntity> buscarUbicacionCantidad(String cantidadUbicacion);

    @Query("Select ubi from UbicacionEntity ubi where ubi.fechaUbicacion=?1")
    List<UbicacionEntity> buscarUbicacionFecha(LocalDate fechaUbicacion);

}