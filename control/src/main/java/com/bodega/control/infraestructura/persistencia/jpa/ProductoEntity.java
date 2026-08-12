package com.bodega.control.infraestructura.persistencia.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * El producto NO lleva la lista de sus lotes ni la de sus detalles de solicitud.
 *
 * Antes las tenia, con cascade = ALL y orphanRemoval = true. El problema es que
 * al guardar un producto se arma una entidad nueva a partir del dominio, y esa
 * entidad nueva viene con las listas vacias. Hibernate entiende entonces que al
 * producto le quitaron TODOS sus lotes y todos sus detalles de solicitud, e
 * intenta borrarlos: editar algo tan inocente como las unidades por caja
 * terminaba borrando el inventario del producto, o fallando con un error de
 * clave foranea si esas filas estaban usadas en otro lado.
 *
 * La relacion la sigue mandando el otro lado (LoteEntity.producto y
 * DetalleSolicitudEntity.producto), que es donde de verdad esta la columna, asi
 * que aqui no hace falta nada.
 */
@Entity
@Data
@Table(name = "producto")
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    @Column(name = "nombre_producto", length = 100, nullable = false)
    private String nombreProducto;

    @Column(name = "codigo_producto", length = 50, nullable = true)
    private String codigoProducto;

    @Column(name = "cantidad_producto")
    private String cantidadProducto;

    @Column(name = "unidades_por_caja")
    private Integer unidadesPorCaja;

    /**
     * ACTIVO o ELIMINADO. Un producto con historial no se puede borrar de la
     * tabla: los movimientos y las entregas apuntan a el. Se da de baja, sale de
     * los listados y de los selectores, y se puede recuperar cuando haga falta.
     * Las filas que ya existian llegan con la columna vacia y se tratan como
     * activas.
     */
    @Column(name = "estado", length = 20)
    private String estado;

}
