package com.translog.api.infraestructura.persistencia.mapeadores;

import com.translog.api.dominio.entidades.Vehiculo;
import com.translog.api.infraestructura.persistencia.jpa.VehiculoEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-21T01:23:35+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class IVehiculoJpaMapperImpl implements IVehiculoJpaMapper {

    @Override
    public Vehiculo toDominio(VehiculoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Vehiculo vehiculo = new Vehiculo();

        vehiculo.setIdVehiculo( entity.getIdVehiculo() );
        vehiculo.setPlaca( entity.getPlaca() );
        vehiculo.setCapacidadMaxima( entity.getCapacidadMaxima() );
        vehiculo.setEstado( entity.getEstado() );

        return vehiculo;
    }

    @Override
    public VehiculoEntity toEntity(Vehiculo nuevoVehiculo) {
        if ( nuevoVehiculo == null ) {
            return null;
        }

        VehiculoEntity vehiculoEntity = new VehiculoEntity();

        vehiculoEntity.setIdVehiculo( nuevoVehiculo.getIdVehiculo() );
        vehiculoEntity.setPlaca( nuevoVehiculo.getPlaca() );
        vehiculoEntity.setCapacidadMaxima( nuevoVehiculo.getCapacidadMaxima() );
        vehiculoEntity.setEstado( nuevoVehiculo.getEstado() );

        return vehiculoEntity;
    }
}
