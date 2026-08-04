package com.bodega.control.infraestructura.persistencia.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "zona")
public class ZonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idZona;

    @Column(name = "nombre_zona", length = 100)
    private String nombreZona;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "capacidad_zona", length = 100)
    private String capacidadZona;

}