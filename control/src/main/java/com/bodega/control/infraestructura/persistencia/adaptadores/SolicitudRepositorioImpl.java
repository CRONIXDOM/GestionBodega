package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Solicitud;
import com.bodega.control.dominio.repositorio.ISolicitudRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.SolicitudEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.ISolicitudJpaMapper;
import com.bodega.control.infraestructura.repositorio.ISolicitudJpaRepositorio;

public class SolicitudRepositorioImpl implements ISolicitudRepositorio {

    private final ISolicitudJpaRepositorio jpaRepositorio;
    private final ISolicitudJpaMapper entityMapper;

    public SolicitudRepositorioImpl(
            ISolicitudJpaRepositorio jpaRepositorio,
            ISolicitudJpaMapper entityMapper) {

        this.jpaRepositorio = jpaRepositorio;
        this.entityMapper = entityMapper;
    }

    @Override
    public Solicitud guardar(Solicitud nuevaSolicitud) {
        SolicitudEntity entidad = entityMapper.toEntity(nuevaSolicitud);
        SolicitudEntity guardado = jpaRepositorio.save(entidad);
        return entityMapper.toDominio(guardado);
    }

    @Override
    public Optional<Solicitud> buscarPorid(int idSolicitud) {
        return jpaRepositorio.findById(idSolicitud)
                .map(entityMapper::toDominio);
    }

    @Override
    public List<Solicitud> listarTodos() {
        return jpaRepositorio.findAll()
                .stream()
                .map(entityMapper::toDominio)
                .toList();
    }

    @Override
    public void eliminar(int idSolicitud) {
        jpaRepositorio.deleteById(idSolicitud);
    }

}