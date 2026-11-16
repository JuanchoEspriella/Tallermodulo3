package co.edu.unbosque.tallersicseven.configuration;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.unbosque.tallersicseven.model.User;
import co.edu.unbosque.tallersicseven.model.enums.Role;
import co.edu.unbosque.tallersicseven.repository.UserRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Value("${security.bootstrap-admin.username:}")
	private String adminUsername;

	@Value("${security.bootstrap-admin.password:}")
	private String adminPassword;

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepo, PasswordEncoder passwordEncoder) {

		return args -> {
			if (adminUsername == null || adminUsername.isBlank()
					|| adminPassword == null || adminPassword.isBlank()) {
				log.info("No se configuró un administrador inicial; se omite su creación.");
				return;
			}

			Optional<User> found = userRepo.findByUsername(adminUsername);
			if (found.isPresent()) {
				log.info("El administrador inicial ya existe; se omite su creación.");
			} else {
				User adminUser = new User(adminUsername, passwordEncoder.encode(adminPassword));
				adminUser.setRole(Role.ADMIN);
				userRepo.save(adminUser);
				log.info("Se creó el administrador inicial configurado.");
			}
		};
	}
}
