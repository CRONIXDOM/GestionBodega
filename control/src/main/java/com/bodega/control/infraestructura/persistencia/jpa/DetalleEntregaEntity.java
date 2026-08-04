package com.bodega.control.infraestructura.persistencia.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "detalle_entrega")
public class DetalleEntregaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalleEntrega;

    @Column(name = "nombre_producto", length = 100)
    private String nombreProducto;

    @Column(name = "cantidad_producto", length = 50)
    private String cantidadProducto;

    @Column(name = "codigo_evento", length = 50)
    private String codigoEvento;

    @Column(name = "nombre_evento", length = 100)
    private String nombreEvento;

    @ManyToOne
    @JoinColumn(name = "ENTREGA_idENTREGA")
    private EntregaEntity entrega;

}