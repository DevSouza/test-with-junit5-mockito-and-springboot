package br.com.devsouza.api.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devsouza.api.domain.User;
import br.com.devsouza.api.repositories.UserRepository;
import br.com.devsouza.api.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findById(Integer id) {
		Optional<User> obj = userRepository.findById(id);
		return obj.orElse(null);
	}

}
