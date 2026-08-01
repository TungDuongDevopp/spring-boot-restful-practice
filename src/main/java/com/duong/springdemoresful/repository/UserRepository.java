package com.duong.springdemoresful.repository;
import java.util.Optional;

import com.duong.springdemoresful.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByName(String name);

	Optional<User> findByNameAndEmail(String name, String email);
}
