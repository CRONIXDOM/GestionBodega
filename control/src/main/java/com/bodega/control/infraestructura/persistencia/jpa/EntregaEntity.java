package com.bodega.control.infraestructura.persistencia.jpa;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "entrega")
public class EntregaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEntrega;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Column(name = "responsable_entrega", length = 100)
    private String responsableEntrega;

}