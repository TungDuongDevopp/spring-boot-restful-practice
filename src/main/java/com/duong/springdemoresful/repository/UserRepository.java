package com.duong.springdemoresful.repository;
import java.util.List;
import java.util.Optional;

import com.duong.springdemoresful.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	List<User> findByRole_Name(String roleName);

	boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);

	@EntityGraph(attributePaths = "role")
    Page<User> findAll(Specification<User> specs, Pageable pageable);

}
