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
@Table(name = "rol")
public class RolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;

    @Column(name = "nombre_rol", length = 100)
    private String nombreRol;

    @Column(name = "descripcion_rol", length = 200)
    private String descripcionRol;

    @Column(name = "modulos", length = 1000)
    private String modulos;

}