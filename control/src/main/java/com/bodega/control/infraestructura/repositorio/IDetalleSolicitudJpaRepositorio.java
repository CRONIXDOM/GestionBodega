package com.bodega.control.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.DetalleSolicitudEntity;

public interface IDetalleSolicitudJpaRepositorio extends JpaRepository<DetalleSolicitudEntity, Integer> {

    @Query("Select det from DetalleSolicitudEntity det")
    List<DetalleSolicitudEntity> listarDetalleSolicitud();

    @Query("Select det from DetalleSolicitudEntity det where det.lugarRecogida=?1")
    List<DetalleSolicitudEntity> buscarPorLugarRecogida(String lugarRecogida);

}