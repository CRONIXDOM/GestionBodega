package com.translog.api.presentacion.mapeadores;

import com.translog.api.dominio.entidades.Vehiculo;
import com.translog.api.presentacion.dto.request.VehiculoRequestDto;
import com.translog.api.presentacion.dto.response.VehiculoResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-21T01:23:35+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class IVehiculoDtoMapperImpl implements IVehiculoDtoMapper {

    @Override
    public Vehiculo toDomain(VehiculoRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Vehiculo vehiculo = new Vehiculo();

        vehiculo.setIdVehiculo( dto.getIdVehiculo() );
        vehiculo.setPlaca( dto.getPlaca() );
        vehiculo.setCapacidadMaxima( dto.getCapacidadMaxima() );
        vehiculo.setEstado( dto.getEstado() );

        return vehiculo;
    }

    @Override
    public VehiculoResponseDto toResponseDto(Vehiculo vehiculo) {
        if ( vehiculo == null ) {
            return null;
        }

        VehiculoResponseDto vehiculoResponseDto = new VehiculoResponseDto();

        vehiculoResponseDto.setIdVehiculo( vehiculo.getIdVehiculo() );
        vehiculoResponseDto.setPlaca( vehiculo.getPlaca() );
        vehiculoResponseDto.setCapacidadMaxima( vehiculo.getCapacidadMaxima() );
        vehiculoResponseDto.setEstado( vehiculo.getEstado() );

        return vehiculoResponseDto;
    }
}
