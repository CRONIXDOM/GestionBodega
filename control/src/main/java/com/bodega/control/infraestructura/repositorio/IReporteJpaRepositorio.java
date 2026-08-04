package com.bodega.control.infraestructura.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.ReporteEntity;

public interface IReporteJpaRepositorio extends JpaRepository<ReporteEntity, Integer> {

    List<ReporteEntity> findByTipoReporte(String tipoReporte);

    @Query("Select rep from ReporteEntity rep")
    List<ReporteEntity> listarReportes();

    @Query("Select rep from ReporteEntity rep where rep.tipoReporte=?1")
    List<ReporteEntity> buscarReporteTipo(String tipoReporte);

    @Query("Select rep from ReporteEntity rep where rep.fechaCreacion=?1")
    List<ReporteEntity> buscarReporteFecha(LocalDate fechaCreacion);

}
