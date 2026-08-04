package com.bodega.control.infraestructura.persistencia.jpa;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "lote")
public class LoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLote;

    @Column(name = "numero_lote", length = 50)
    private String numeroLote;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "cantidad_lote")
    private Integer cantidadLote;

    @Column(name = "cantidad_reservada")
    private Integer cantidadReservada = 0;

    @ManyToOne
    @JoinColumn(name = "PRODUCTO_idPRODUCTO",nullable = false)
    private ProductoEntity producto;

    @ManyToOne
    @JoinColumn(name = "UBICACION_idUBICACION")
    private UbicacionEntity ubicacion;

    @OneToMany(mappedBy = "lote")
    private List<RegistroEntity> registros;

}
