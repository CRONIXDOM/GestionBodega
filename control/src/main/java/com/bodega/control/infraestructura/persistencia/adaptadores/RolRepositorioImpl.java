package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Rol;
import com.bodega.control.dominio.repositorio.IRolRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.RolEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.IRolJpaMapper;
import com.bodega.control.infraestructura.repositorio.IRolJpaRepositorio;

public class RolRepositorioImpl implements IRolRepositorio {

    private final IRolJpaRepositorio jpaRepositorio;
    private final IRolJpaMapper entityMapper;

    public RolRepositorioImpl(
            IRolJpaRepositorio jpaRepositorio,
            IRolJpaMapper entityMapper) {

        this.jpaRepositorio = jpaRepositorio;
        this.entityMapper = entityMapper;
    }

    @Override
    public Rol guardar(Rol nuevoRol) {
        RolEntity entidad = entityMapper.toEntity(nuevoRol);
        RolEntity guardado = jpaRepositorio.save(entidad);
        return entityMapper.toDominio(guardado);
    }

    @Override
    public Optional<Rol> buscarPorid(int idRol) {
        return jpaRepositorio.findById(idRol)
                .map(entityMapper::toDominio);
    }

    @Override
    public List<Rol> listarTodos() {
        return jpaRepositorio.findAll()
                .stream()
                .map(entityMapper::toDominio)
                .toList();
    }

    @Override
    public void eliminar(int idRol) {
        jpaRepositorio.deleteById(idRol);
    }

}