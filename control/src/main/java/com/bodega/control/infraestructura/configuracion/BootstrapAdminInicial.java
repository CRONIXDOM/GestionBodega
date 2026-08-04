package com.bodega.control.infraestructura.configuracion;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bodega.control.aplicacion.casosuso.entrada.ICredencialesUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IRolUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IUsuarioRolUseCase;
import com.bodega.control.aplicacion.casosuso.entrada.IUsuarioUseCase;
import com.bodega.control.dominio.entidades.Credenciales;
import com.bodega.control.dominio.entidades.Rol;
import com.bodega.control.dominio.entidades.Usuario;
import com.bodega.control.dominio.entidades.UsuarioRol;

/**
 * Si la base de datos está completamente vacía de usuarios, crea automáticamente
 * un usuario "admin" con rol Administrador al arrancar la aplicación, para que
 * siempre exista una forma de entrar al panel sin tener que crearlo a mano por API.
 */
@Component
public class BootstrapAdminInicial implements CommandLineRunner {

	private static final String USUARIO_INICIAL = "admin";
	private static final String CONTRASENA_INICIAL = "admin123";

	private final IUsuarioUseCase usuarioUseCase;
	private final IRolUseCase rolUseCase;
	private final ICredencialesUseCase credencialesUseCase;
	private final IUsuarioRolUseCase usuarioRolUseCase;

	public BootstrapAdminInicial(IUsuarioUseCase usuarioUseCase, IRolUseCase rolUseCase,
			ICredencialesUseCase credencialesUseCase, IUsuarioRolUseCase usuarioRolUseCase) {
		this.usuarioUseCase = usuarioUseCase;
		this.rolUseCase = rolUseCase;
		this.credencialesUseCase = credencialesUseCase;
		this.usuarioRolUseCase = usuarioRolUseCase;
	}

	@Override
	public void run(String... args) {
		List<Usuario> usuariosExistentes = usuarioUseCase.listarTodos();
		if (!usuariosExistentes.isEmpty()) {
			return;
		}

		Usuario admin = usuarioUseCase.guardar(new Usuario(null, USUARIO_INICIAL, "Sistema", "Activo"));

		Rol rolAdministrador = rolUseCase.listarTodos().stream()
				.filter(r -> "Administrador".equalsIgnoreCase(r.getNombreRol())).findFirst()
				.orElseGet(() -> rolUseCase.guardar(new Rol(null, "Administrador", "Acceso total")));

		credencialesUseCase
				.guardar(new Credenciales(null, USUARIO_INICIAL, "admin@bodega.com", CONTRASENA_INICIAL));

		usuarioRolUseCase.guardar(new UsuarioRol(null, LocalDate.now(), admin, rolAdministrador));

		System.out.println("=====================================================");
		System.out.println(" Usuario administrador inicial creado automaticamente");
		System.out.println(" Usuario:    " + USUARIO_INICIAL);
		System.out.println(" Contrasena: " + CONTRASENA_INICIAL);
		System.out.println("=====================================================");
	}
}
