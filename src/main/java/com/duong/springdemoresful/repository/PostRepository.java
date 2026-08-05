package com.duong.springdemoresful.repository;

import com.duong.springdemoresful.model.Post;
import com.duong.springdemoresful.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository  extends JpaRepository<Post,Long> {

    List<Post> findByTagsContains(Tag tag);
}
