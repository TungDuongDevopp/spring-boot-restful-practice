package com.duong.springdemoresful.repository;
import java.util.List;
import java.util.Optional;

import com.duong.springdemoresful.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByName(String name);


	List<User> findByRole_Name(String roleName);

	Optional<User> findByNameAndEmail(String name, String email);

	boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);
}
