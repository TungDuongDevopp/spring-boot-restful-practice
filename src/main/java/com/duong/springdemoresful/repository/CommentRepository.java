package com.duong.springdemoresful.repository;

import com.duong.springdemoresful.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository  extends JpaRepository<Comment,Long> {
}
