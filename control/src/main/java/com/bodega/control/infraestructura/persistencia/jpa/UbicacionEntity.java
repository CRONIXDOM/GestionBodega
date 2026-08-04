package com.bodega.control.infraestructura.persistencia.jpa;

import java.time.LocalDate;

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
@Table(name = "ubicacion")
public class UbicacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUbicacion;

    @Column(name = "codigo_ubicacion", length = 50)
    private String codigoUbicacion;

    @Column(name = "cantidad_ubicacion", length = 100)
    private String cantidadUbicacion;

    @Column(name = "fecha_ubicacion")
    private LocalDate fechaUbicacion;

    @ManyToOne
    @JoinColumn(name = "ZONA_idZONA")
    private ZonaEntity zona;

    @ManyToOne
    @JoinColumn(name = "SEDE_idSEDE")
    private SedeEntity sede;

}
