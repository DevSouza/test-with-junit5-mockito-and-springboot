package br.com.devsouza.api.services;

import java.util.List;

import br.com.devsouza.api.domain.User;

public interface UserService {

	User findById(Integer id);
	List<User> findAll();
	
}
