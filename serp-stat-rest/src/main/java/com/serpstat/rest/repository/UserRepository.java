package com.serpstat.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serpstat.rest.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByLogin(String login);
	void deleteByLogin(String login);

// Previous Code
//	User findById(long id); <-- suppported by JPA
//	User findByLogin(String login);
//	List<User> findAllUsers(); <-- suppported by JPA
//	void save(User user); <-- suppported by JPA
//	void deleteByLogin(String login);
}
