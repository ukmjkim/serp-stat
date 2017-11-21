package com.serpstat.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serpstat.rest.domain.User;

//https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html
//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords
//spring boot namedquery join query
//https://stackoverflow.com/questions/13154818/how-to-define-a-jpa-repository-query-with-a-join
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
