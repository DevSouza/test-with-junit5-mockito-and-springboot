package br.com.devsouza.api.services;

import br.com.devsouza.api.domain.User;

public interface UserService {

	User findById(Integer id);
	
}
