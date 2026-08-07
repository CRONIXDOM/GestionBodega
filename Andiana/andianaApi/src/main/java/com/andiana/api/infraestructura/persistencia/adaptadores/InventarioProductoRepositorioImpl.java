package com.andiana.api.infraestructura.persistencia.adaptadores;

import java.util.List;
import java.util.Optional;

import com.andiana.api.dominio.entidades.InventarioProducto;
import com.andiana.api.dominio.repositorio.IInventarioProductoRepositorio;
import com.andiana.api.infraestructura.persistencia.jpa.InventarioProductoEntity;
import com.andiana.api.infraestructura.persistencia.mapeadores.IInventarioProductoJpaMapper;
import com.andiana.api.infraestructura.repositorio.IInventarioProductoJpaRepositorio;

public class InventarioProductoRepositorioImpl implements IInventarioProductoRepositorio {

    private final IInventarioProductoJpaRepositorio jpaRepositorio;
    private final IInventarioProductoJpaMapper entityMapper;

    public InventarioProductoRepositorioImpl(
            IInventarioProductoJpaRepositorio jpaRepositorio,
            IInventarioProductoJpaMapper entityMapper) {

        this.jpaRepositorio = jpaRepositorio;
        this.entityMapper = entityMapper;
    }

    @Override
    public InventarioProducto guardar(InventarioProducto nuevoInventarioProducto) {
        InventarioProductoEntity entidad = entityMapper.toEntity(nuevoInventarioProducto);
        InventarioProductoEntity guardado = jpaRepositorio.save(entidad);
        return entityMapper.toDominio(guardado);
    }

    @Override
    public Optional<InventarioProducto> buscarPorid(int idInventarioProducto) {
        return jpaRepositorio.findById(idInventarioProducto)
                .map(entityMapper::toDominio);
    }

    @Override
    public List<InventarioProducto> listarTodos() {
        return jpaRepositorio.findAll()
                .stream()
                .map(entityMapper::toDominio)
                .toList();
    }

    @Override
    public void eliminar(int idInventarioProducto) {
        jpaRepositorio.deleteById(idInventarioProducto);
    }

    @Override
    public List<InventarioProducto> buscarPorLote(int idLote) {
        return jpaRepositorio.findByIdLote(idLote)
                .stream()
                .map(entityMapper::toDominio)
                .toList();
    }
}
