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
@Table(name = "credenciales")
public class CredencialesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCredenciales;

    @Column(name = "usuario", length = 80)
    private String usuario;

    @Column(name = "correo", length = 100)
    private String correo;

    @Column(name = "contrasena", length = 255)
    private String contrasena;

    
}