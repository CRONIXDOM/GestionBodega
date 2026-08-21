package com.translog.api.presentacion.mapeadores;

import com.translog.api.dominio.entidades.Despacho;
import com.translog.api.presentacion.dto.request.DespachoRequestDto;
import com.translog.api.presentacion.dto.response.DespachoResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-21T01:23:35+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class IDespachoDtoMapperImpl implements IDespachoDtoMapper {

    @Override
    public Despacho toDomain(DespachoRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Despacho despacho = new Despacho();

        despacho.setIdDespacho( dto.getIdDespacho() );
        despacho.setFechaDespacho( dto.getFechaDespacho() );
        despacho.setIdRuta( dto.getIdRuta() );
        despacho.setIdVehiculo( dto.getIdVehiculo() );
        despacho.setIdConductor( dto.getIdConductor() );
        despacho.setEstado( dto.getEstado() );

        return despacho;
    }

    @Override
    public DespachoResponseDto toResponseDto(Despacho despacho) {
        if ( despacho == null ) {
            return null;
        }

        DespachoResponseDto despachoResponseDto = new DespachoResponseDto();

        despachoResponseDto.setIdDespacho( despacho.getIdDespacho() );
        despachoResponseDto.setFechaDespacho( despacho.getFechaDespacho() );
        despachoResponseDto.setIdRuta( despacho.getIdRuta() );
        despachoResponseDto.setIdVehiculo( despacho.getIdVehiculo() );
        despachoResponseDto.setIdConductor( despacho.getIdConductor() );
        despachoResponseDto.setEstado( despacho.getEstado() );

        return despachoResponseDto;
    }
}
