package br.com.devsouza.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devsouza.api.domain.User;
import br.com.devsouza.api.domain.dto.UserDTO;
import br.com.devsouza.api.repositories.UserRepository;
import br.com.devsouza.api.services.UserService;
import br.com.devsouza.api.services.exceptions.DataIntegratyViolationException;
import br.com.devsouza.api.services.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findById(Integer id) {
		Optional<User> obj = userRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User create(UserDTO obj) {
		findByEmail(obj);
		return userRepository.save(mapper.map(obj, User.class));
	}

	private void findByEmail(UserDTO obj) {
		Optional<User> user = userRepository.findByEmail(obj.getEmail());
		if(user.isPresent()) {
			throw new DataIntegratyViolationException("E-mail já cadastrado no sistema.");
		}
	}

}
