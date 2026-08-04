package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Reporte;
import com.bodega.control.dominio.repositorio.IReporteRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.ReporteEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.IReporteJpaMapper;
import com.bodega.control.infraestructura.repositorio.IReporteJpaRepositorio;

public class ReporteRepositorioImpl implements IReporteRepositorio {

    private final IReporteJpaRepositorio jpaRepositorio;
    private final IReporteJpaMapper entityMapper;

    public ReporteRepositorioImpl(
            IReporteJpaRepositorio jpaRepositorio,
            IReporteJpaMapper entityMapper) {

        this.jpaRepositorio = jpaRepositorio;
        this.entityMapper = entityMapper;
    }

    @Override
    public Reporte guardar(Reporte nuevoReporte) {
        ReporteEntity entidad = entityMapper.toEntity(nuevoReporte);
        ReporteEntity guardado = jpaRepositorio.save(entidad);
        return entityMapper.toDominio(guardado);
    }

    @Override
    public Optional<Reporte> buscarPorid(int idReporte) {
        return Optional.empty();
    }

    @Override
    public List<Reporte> listarTodos() {
        return jpaRepositorio.findAll().stream().map(entityMapper::toDominio).toList();
    }

    @Override
    public void eliminar(int idReporte) {
        jpaRepositorio.deleteById(idReporte);
    }

}