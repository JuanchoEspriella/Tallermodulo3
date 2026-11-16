package co.edu.unbosque.tallersicseven.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.tallersicseven.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByUsername(String username);
	public void deleteByUsername(String username); 
}
