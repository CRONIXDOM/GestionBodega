package com.bodega.control.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bodega.control.infraestructura.persistencia.jpa.CredencialesEntity;

public interface ICredencialesJpaRepositorio extends JpaRepository<CredencialesEntity, Integer> {

    List<CredencialesEntity> findByUsuario(String usuario);

    @Query("Select cred from CredencialesEntity cred")
    List<CredencialesEntity> listarCredenciales();

    // sin distinguir mayusculas: el usuario se guarda en mayusculas, pero el
    // formulario de acceso debe seguir funcionando se escriba como se escriba
    // (y tambien con las cuentas creadas antes de esa regla).
    @Query("Select cred from CredencialesEntity cred where upper(cred.usuario)=upper(?1)")
    List<CredencialesEntity> buscarCredencialesNombre(String usuario);

    @Query("Select cred from CredencialesEntity cred where cred.usuario=?1 and cred.correo=?2")
    List<CredencialesEntity> buscarCredencialesEstado(String usuario, boolean estado);

}