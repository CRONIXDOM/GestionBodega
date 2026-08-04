package com.bodega.control.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.bodega.control.dominio.entidades.Registro;
import com.bodega.control.dominio.repositorio.IRegistroRepositorio;
import com.bodega.control.infraestructura.persistencia.jpa.RegistroEntity;
import com.bodega.control.infraestructura.persistencia.mapeadores.IRegistroJpaMapper;
import com.bodega.control.infraestructura.repositorio.IRegistroJpaRepositorio;

public class RegistroRepositorioImpl implements IRegistroRepositorio {

    private final IRegistroJpaRepositorio jpaRepositorio;
    private final IRegistroJpaMapper entityMapper;

    public RegistroRepositorioImpl(
            IRegistroJpaRepositorio jpaRepositorio,
            IRegistroJpaMapper entityMapper) {

        this.jpaRepositorio = jpaRepositorio;
        this.entityMapper = entityMapper;
    }

    @Override
    public Registro guardar(Registro nuevoRegistro) {
        RegistroEntity entidad = entityMapper.toEntity(nuevoRegistro);
        RegistroEntity guardado = jpaRepositorio.save(entidad);
        return entityMapper.toDominio(guardado);
    }

    @Override
    public Optional<Registro> buscarPorid(int idRegistro) {
        return jpaRepositorio.findById(idRegistro)
                .map(entityMapper::toDominio);
    }

    @Override
    public List<Registro> listarTodos() {
        return jpaRepositorio.findAll()
                .stream()
                .map(entityMapper::toDominio)
                .toList();
    }

    @Override
    public void eliminar(int idRegistro) {
        jpaRepositorio.deleteById(idRegistro);
    }

}