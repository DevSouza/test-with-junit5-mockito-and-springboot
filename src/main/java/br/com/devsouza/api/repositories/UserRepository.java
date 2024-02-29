package br.com.devsouza.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.devsouza.api.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
}
