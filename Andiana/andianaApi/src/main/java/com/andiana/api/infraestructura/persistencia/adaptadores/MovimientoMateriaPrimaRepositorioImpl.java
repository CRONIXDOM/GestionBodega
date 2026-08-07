package com.andiana.api.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.MovimientoMateriaPrima;
import com.andiana.api.dominio.repositorio.IMovimientoMateriaPrimaRepositorio;
import com.andiana.api.infraestructura.persistencia.jpa.MovimientoMateriaPrimaEntity;
import com.andiana.api.infraestructura.persistencia.mapeadores.IMovimientoMateriaPrimaJpaMapper;
import com.andiana.api.infraestructura.repositorio.IMovimientoMateriaPrimaJpaRepositorio;

public class MovimientoMateriaPrimaRepositorioImpl implements IMovimientoMateriaPrimaRepositorio {

    private final IMovimientoMateriaPrimaJpaRepositorio jpaRepositorio;
    private final IMovimientoMateriaPrimaJpaMapper entityMapper;

    public MovimientoMateriaPrimaRepositorioImpl(
            IMovimientoMateriaPrimaJpaRepositorio jpaRepositorio,
            IMovimientoMateriaPrimaJpaMapper entityMapper) {

        this.jpaRepositorio = jpaRepositorio;
        this.entityMapper = entityMapper;
    }

    @Override
    public MovimientoMateriaPrima guardar(MovimientoMateriaPrima nuevoMovimientoMateriaPrima) {
        MovimientoMateriaPrimaEntity entidad = entityMapper.toEntity(nuevoMovimientoMateriaPrima);
        MovimientoMateriaPrimaEntity guardado = jpaRepositorio.save(entidad);
        return entityMapper.toDominio(guardado);
    }

    @Override
    public Optional<MovimientoMateriaPrima> buscarPorid(int idMovimientoMateriaPrima) {
        return jpaRepositorio.findById(idMovimientoMateriaPrima)
                .map(entityMapper::toDominio);
    }

    @Override
    public List<MovimientoMateriaPrima> listarTodos() {
        return jpaRepositorio.findAll()
                .stream()
                .map(entityMapper::toDominio)
                .toList();
    }

    @Override
    public void eliminar(int idMovimientoMateriaPrima) {
        jpaRepositorio.deleteById(idMovimientoMateriaPrima);
    }

    @Override
    public List<MovimientoMateriaPrima> buscarPorMateria(int idMateria) {
        return jpaRepositorio.findByIdMateriaOrderByFechaAscIdMovimientoAsc(idMateria)
                .stream()
                .map(entityMapper::toDominio)
                .toList();
    }
}
