package com.duong.springdemoresful.repository;

import com.duong.springdemoresful.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role,Long> {

    boolean existsByName(String name);


    Optional<Role> findByIdOrName(Long id,String name);
}
