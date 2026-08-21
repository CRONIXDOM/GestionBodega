package com.translog.api.presentacion.mapeadores;

import com.translog.api.dominio.entidades.Envio;
import com.translog.api.presentacion.dto.request.EnvioRequestDto;
import com.translog.api.presentacion.dto.response.EnvioResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-21T01:23:35+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class IEnvioDtoMapperImpl implements IEnvioDtoMapper {

    @Override
    public Envio toDomain(EnvioRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Envio envio = new Envio();

        envio.setIdEnvio( dto.getIdEnvio() );
        envio.setIdCiudadOrigen( dto.getIdCiudadOrigen() );
        envio.setIdCiudadDestino( dto.getIdCiudadDestino() );
        envio.setPeso( dto.getPeso() );
        envio.setFechaRegistro( dto.getFechaRegistro() );
        envio.setValorDeclarado( dto.getValorDeclarado() );
        envio.setEstado( dto.getEstado() );
        envio.setIdDespacho( dto.getIdDespacho() );

        return envio;
    }

    @Override
    public EnvioResponseDto toResponseDto(Envio envio) {
        if ( envio == null ) {
            return null;
        }

        EnvioResponseDto envioResponseDto = new EnvioResponseDto();

        envioResponseDto.setIdEnvio( envio.getIdEnvio() );
        envioResponseDto.setIdCiudadOrigen( envio.getIdCiudadOrigen() );
        envioResponseDto.setIdCiudadDestino( envio.getIdCiudadDestino() );
        envioResponseDto.setPeso( envio.getPeso() );
        envioResponseDto.setFechaRegistro( envio.getFechaRegistro() );
        envioResponseDto.setValorDeclarado( envio.getValorDeclarado() );
        envioResponseDto.setEstado( envio.getEstado() );
        envioResponseDto.setIdDespacho( envio.getIdDespacho() );

        return envioResponseDto;
    }
}
