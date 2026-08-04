package com.bodega.control.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.ZonaEntity;

public interface IZonaJpaRepositorio extends JpaRepository<ZonaEntity, Integer> {

    List<ZonaEntity> findByNombreZona(String nombreZona);

    List<ZonaEntity> findByCapacidadZona(String capacidadZona);

    @Query("Select zon from ZonaEntity zon")
    List<ZonaEntity> listarZonas();

    @Query("Select zon from ZonaEntity zon where zon.nombreZona=?1")
    List<ZonaEntity> buscarZonaNombre(String nombreZona);

    @Query("Select zon from ZonaEntity zon where zon.capacidadZona=?1")
    List<ZonaEntity> buscarZonaCapacidad(String capacidadZona);

}