package br.com.devsouza.api.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

	@Test
	void whenFindByIdReturnSuccess() {
		when(userService.findById(anyInt())).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(userDTO);
		
		ResponseEntity<UserDTO> response = userResource.findById(ID);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UserDTO.class, response.getBody().getClass());
		
		assertEquals(ID, response.getBody().getId());
		assertEquals(NAME, response.getBody().getName());
		assertEquals(EMAIL, response.getBody().getEmail());
		assertEquals(PASSWORD, response.getBody().getPassword());
	}
	
	@Test
	void whenFindAllReturnSuccess() {
		when(userService.findAll()).thenReturn(List.of(user));
		when(mapper.map(any(), any())).thenReturn(userDTO);
		
		ResponseEntity<List<UserDTO>> response = userResource.findAll();
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		//assertEquals(ArrayList.class, response.getBody().getClass());
		assertEquals(UserDTO.class, response.getBody().get(0).getClass());
		
		assertEquals(ID, response.getBody().get(0).getId());
		assertEquals(NAME, response.getBody().get(0).getName());
		assertEquals(EMAIL, response.getBody().get(0).getEmail());
		assertEquals(PASSWORD, response.getBody().get(0).getPassword());
	}
	
	@Test
	void whenCreateThenReturnCreated() {
		when(userService.create(any())).thenReturn(user);
		
		ResponseEntity<?> response = userResource.create(userDTO);
		
		assertNotNull(response);
		assertNull(response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		
		assertNotNull(response.getHeaders().getLocation());
	}
	
	@Test
	void whenUpdateThenReturnSuccess() {
		when(userService.update(userDTO)).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(userDTO);
		
		ResponseEntity<UserDTO> response = userResource.update(ID, userDTO);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UserDTO.class, response.getBody().getClass());
		
		assertEquals(ID, response.getBody().getId());
		assertEquals(NAME, response.getBody().getName());
		assertEquals(EMAIL, response.getBody().getEmail());
	}
	
	@Test
	void whenDeleteThenReturnSuccess() {
		doNothing().when(userService).delete(anyInt());
		
		ResponseEntity<?> response = userResource.delete(ID);
		
		assertNotNull(response);
		assertNull(response.getBody());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(userService, times(1)).delete(anyInt());
	}
	
	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
	}

}
