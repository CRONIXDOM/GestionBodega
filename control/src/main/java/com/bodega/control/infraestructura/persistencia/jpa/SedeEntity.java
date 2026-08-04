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
@Table(name = "sede")
public class SedeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSede;

    @Column(name = "nombre_sede", length = 100)
    private String nombreSede;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

}
