package com.example.security;

import com.example.security.config.BcryptConfig;
import com.example.security.constants.PerfilEnum;
import com.example.security.model.Usuario;
import com.example.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecurityApplication {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {

			Usuario usuario = new Usuario();
			usuario.setUsername("user");
			usuario.setRole(PerfilEnum.ROLE_USER);
			usuario.setSenha(BcryptConfig.gerarBcrypt("123456"));
			this.usuarioRepository.save(usuario);

			Usuario admin = new Usuario();
			admin.setUsername("admin");
			admin.setRole(PerfilEnum.ROLE_ADMIN);
			admin.setSenha(BcryptConfig.gerarBcrypt("123456"));
			this.usuarioRepository.save(admin);

		};
	}

}
