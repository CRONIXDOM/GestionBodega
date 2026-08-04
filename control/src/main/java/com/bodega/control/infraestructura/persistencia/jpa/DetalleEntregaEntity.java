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

    @ManyToOne
    @JoinColumn(name = "PRODUCTO_idPRODUCTO")
    private ProductoEntity producto;

    @Column(name = "cantidad_producto")
    private Integer cantidadProducto;

    @Column(name = "codigo_evento", length = 50)
    private String codigoEvento;

    @Column(name = "nombre_evento", length = 100)
    private String nombreEvento;

    @ManyToOne
    @JoinColumn(name = "ENTREGA_idENTREGA")
    private EntregaEntity entrega;

    @ManyToOne
    @JoinColumn(name = "DETALLESOLICITUD_idDETALLESOLICITUD")
    private DetalleSolicitudEntity detalleSolicitud;

    @ManyToOne
    @JoinColumn(name = "LOTE_idLOTE")
    private LoteEntity lote;

}
