package br.com.devsouza.api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.devsouza.api.domain.User;
import br.com.devsouza.api.repositories.UserRepository;

@Configuration
@Profile("local")
public class LocalConfig {

	@Autowired private UserRepository userRepository;
	
	@Bean
	public void startDB() {
		User u1 = new User(null, "Valdir","val@gmail.com","123");
		User u2 = new User(null, "Luiz","luiz@gmail.com","123");
		
		userRepository.saveAll(List.of(u1, u2));
	}
	
}
