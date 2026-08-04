package com.bodega.control.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.ProductoEntity;

public interface IProductoJpaRepositorio extends JpaRepository<ProductoEntity, Integer> {

    List<ProductoEntity> findByNombreProducto(String nombreProducto);

    List<ProductoEntity> findByCodigoProducto(String codigoProducto);

    @Query("Select pro from ProductoEntity pro")
    List<ProductoEntity> listarProductos();

    @Query("Select pro from ProductoEntity pro where pro.nombreProducto=?1")
    List<ProductoEntity> buscarProductoNombre(String nombreProducto);

    @Query("Select pro from ProductoEntity pro where pro.codigoProducto=?1")
    List<ProductoEntity> buscarProductoCodigo(String codigoProducto);

}