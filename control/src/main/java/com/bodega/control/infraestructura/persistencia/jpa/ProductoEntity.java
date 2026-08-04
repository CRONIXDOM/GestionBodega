package com.bodega.control.infraestructura.persistencia.jpa;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

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
    
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoteEntity> lotes = new ArrayList<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleSolicitudEntity> detalleSolicitudes = new ArrayList<>();

}
