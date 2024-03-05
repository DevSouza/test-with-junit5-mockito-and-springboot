package br.com.devsouza.api.services.impl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import br.com.devsouza.api.domain.User;
import br.com.devsouza.api.domain.dto.UserDTO;
import br.com.devsouza.api.repositories.UserRepository;
import br.com.devsouza.api.services.exceptions.DataIntegratyViolationException;
import br.com.devsouza.api.services.exceptions.ObjectNotFoundException;

class UserServiceImplTest {
	private static final int ID 		 = 1;
	private static final String NAME 	 = "Valdir";
	private static final String EMAIL 	 = "valdir@gmail.com";
	private static final String PASSWORD = "123";

	@InjectMocks 
	private UserServiceImpl service;
	
	@Mock private UserRepository repository;
	@Mock private ModelMapper mapper;
	
	private User user;
	private UserDTO userDTO;
	private Optional<User> optionalUser;
	private Optional<User> optionalOtherUser;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}
	
	@Test
	void whenFindByIdThenReturnUserInstance() {
		when(repository.findById(anyInt())).thenReturn(optionalUser);
		
		User response = service.findById(ID);
		
		assertNotNull(response);
		
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
	}
	
	@Test
	void whenFindByIdThenAnObjectNotFoundException() {
		when(repository.findById(anyInt())).thenReturn(Optional.empty());
		
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> service.findById(ID));
		assertEquals("Objeto não encontrado", exception.getMessage());
	}
	
	@Test
	void whenFindAllThenReturnAListOfUsers() {
		when(repository.findAll()).thenReturn(List.of(user));

		List<User> response = service.findAll();
		
		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(User.class, response.get(0).getClass());
		
		assertEquals(ID, response.get(0).getId());
		assertEquals(NAME, response.get(0).getName());
		assertEquals(EMAIL, response.get(0).getEmail());
		assertEquals(PASSWORD, response.get(0).getPassword());
	}
	
	@Test
	void whenCreateThenReturnSuccess() {
		when(repository.save(any())).thenReturn(user);
		
		User response = service.create(userDTO);
		
		assertNotNull(response);
		
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
		assertEquals(PASSWORD, response.getPassword());
		
	}
	
	@Test
	void whenCreateThenReturnAnDataIntegrityViolationException() {
		when(repository.findByEmail(anyString())).thenReturn(optionalOtherUser);
		
		DataIntegratyViolationException exception = assertThrows(DataIntegratyViolationException.class, () -> service.create(userDTO));
		assertEquals("E-mail já cadastrado no sistema.", exception.getMessage());
	}

	@Test
	void whenUpdateThenReturnSuccess() {
		when(repository.save(any())).thenReturn(user);
		
		User response = service.update(userDTO);
		
		assertNotNull(response);
		
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
		assertEquals(PASSWORD, response.getPassword());
		
	}
	
	@Test
	void whenUpdateThenReturnAnDataIntegrityViolationException() {
		when(repository.findByEmail(anyString())).thenReturn(optionalOtherUser);
		
		DataIntegratyViolationException exception = assertThrows(DataIntegratyViolationException.class, () -> service.update(userDTO));
		assertEquals("E-mail já cadastrado no sistema.", exception.getMessage());
	}
	
	@Test
	void deleteWithSuccess() {
		when(repository.findById(anyInt())).thenReturn(optionalOtherUser);
		doNothing().when(repository).deleteById(anyInt());

		service.delete(ID);
		
		verify(repository, times(1)).deleteById(anyInt());
	}
	
	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
		optionalUser = Optional.of(user);
		optionalOtherUser = Optional.of(new User(2, NAME, EMAIL, PASSWORD));
	}
}
