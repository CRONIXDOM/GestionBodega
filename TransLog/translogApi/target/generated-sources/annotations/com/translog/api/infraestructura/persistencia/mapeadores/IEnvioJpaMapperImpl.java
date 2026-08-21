package com.translog.api.infraestructura.persistencia.mapeadores;

import com.translog.api.dominio.entidades.Envio;
import com.translog.api.infraestructura.persistencia.jpa.EnvioEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-21T01:23:35+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class IEnvioJpaMapperImpl implements IEnvioJpaMapper {

    @Override
    public Envio toDominio(EnvioEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Envio envio = new Envio();

        envio.setIdEnvio( entity.getIdEnvio() );
        envio.setIdCiudadOrigen( entity.getIdCiudadOrigen() );
        envio.setIdCiudadDestino( entity.getIdCiudadDestino() );
        envio.setPeso( entity.getPeso() );
        envio.setFechaRegistro( entity.getFechaRegistro() );
        envio.setValorDeclarado( entity.getValorDeclarado() );
        envio.setEstado( entity.getEstado() );
        envio.setIdDespacho( entity.getIdDespacho() );

        return envio;
    }

    @Override
    public EnvioEntity toEntity(Envio nuevoEnvio) {
        if ( nuevoEnvio == null ) {
            return null;
        }

        EnvioEntity envioEntity = new EnvioEntity();

        envioEntity.setIdEnvio( nuevoEnvio.getIdEnvio() );
        envioEntity.setIdCiudadOrigen( nuevoEnvio.getIdCiudadOrigen() );
        envioEntity.setIdCiudadDestino( nuevoEnvio.getIdCiudadDestino() );
        envioEntity.setPeso( nuevoEnvio.getPeso() );
        envioEntity.setFechaRegistro( nuevoEnvio.getFechaRegistro() );
        envioEntity.setValorDeclarado( nuevoEnvio.getValorDeclarado() );
        envioEntity.setEstado( nuevoEnvio.getEstado() );
        envioEntity.setIdDespacho( nuevoEnvio.getIdDespacho() );

        return envioEntity;
    }
}
