package br.com.devsouza.api.services;

import java.util.List;

import br.com.devsouza.api.domain.User;
import br.com.devsouza.api.domain.dto.UserDTO;

public interface UserService {

	User findById(Integer id);
	List<User> findAll();
	User create(UserDTO obj);
	User update(UserDTO obj);

}
