package br.com.devsouza.api.resources;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.devsouza.api.domain.User;
import br.com.devsouza.api.domain.dto.UserDTO;
import br.com.devsouza.api.services.UserService;


@SpringBootTest
class UserResourceTest {
	
	private static final int ID 		 = 1;
	private static final String NAME 	 = "Valdir";
	private static final String EMAIL 	 = "valdir@gmail.com";
	private static final String PASSWORD = "123";

	@InjectMocks
	private UserResource userResource;
	
	@Mock private UserService userService;
	@Mock private ModelMapper mapper;
	
	private User user;
	private UserDTO userDTO;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
	}

}
