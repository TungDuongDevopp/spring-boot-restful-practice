package com.duong.springdemoresful.repository;

import com.duong.springdemoresful.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository  extends JpaRepository<Tag,Long> {

    boolean existsByName(String name);
}
