package com.bodega.control.infraestructura.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.SolicitudEntity;

public interface ISolicitudJpaRepositorio extends JpaRepository<SolicitudEntity, Integer> {

    List<SolicitudEntity> findByEstadoSolicitud(Boolean estadoSolicitud);

    List<SolicitudEntity> findByFechaSolicitud(LocalDate fechaSolicitud);

    @Query("Select sol from SolicitudEntity sol")
    List<SolicitudEntity> listarSolicitudes();

    @Query("Select sol from SolicitudEntity sol where sol.estadoSolicitud=?1")
    List<SolicitudEntity> buscarSolicitudEstado(Boolean estadoSolicitud);

    @Query("Select sol from SolicitudEntity sol where sol.fechaSolicitud=?1")
    List<SolicitudEntity> buscarSolicitudFecha(LocalDate fechaSolicitud);

}