package com.andiana.api.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.OrdenProduccion;
import com.andiana.api.dominio.repositorio.IOrdenProduccionRepositorio;
import com.andiana.api.infraestructura.persistencia.jpa.OrdenProduccionEntity;
import com.andiana.api.infraestructura.persistencia.mapeadores.IOrdenProduccionJpaMapper;
import com.andiana.api.infraestructura.repositorio.IOrdenProduccionJpaRepositorio;

public class OrdenProduccionRepositorioImpl implements IOrdenProduccionRepositorio {

    private final IOrdenProduccionJpaRepositorio jpaRepositorio;
    private final IOrdenProduccionJpaMapper entityMapper;

    public OrdenProduccionRepositorioImpl(
            IOrdenProduccionJpaRepositorio jpaRepositorio,
            IOrdenProduccionJpaMapper entityMapper) {

        this.jpaRepositorio = jpaRepositorio;
        this.entityMapper = entityMapper;
    }

    @Override
    public OrdenProduccion guardar(OrdenProduccion nuevoOrdenProduccion) {
        OrdenProduccionEntity entidad = entityMapper.toEntity(nuevoOrdenProduccion);
        OrdenProduccionEntity guardado = jpaRepositorio.save(entidad);
        return entityMapper.toDominio(guardado);
    }

    @Override
    public Optional<OrdenProduccion> buscarPorid(int idOrdenProduccion) {
        return jpaRepositorio.findById(idOrdenProduccion)
                .map(entityMapper::toDominio);
    }

    @Override
    public List<OrdenProduccion> listarTodos() {
        return jpaRepositorio.findAll()
                .stream()
                .map(entityMapper::toDominio)
                .toList();
    }

    @Override
    public void eliminar(int idOrdenProduccion) {
        jpaRepositorio.deleteById(idOrdenProduccion);
    }
}
