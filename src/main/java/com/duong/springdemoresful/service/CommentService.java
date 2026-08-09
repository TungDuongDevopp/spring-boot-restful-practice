package com.duong.springdemoresful.service;

import com.duong.springdemoresful.dto.request.CommentFilterRequest;
import com.duong.springdemoresful.dto.request.CommentRequest;
import com.duong.springdemoresful.dto.response.CommentResponse;
import com.duong.springdemoresful.helper.exception.ResourceNotFoundException;
import com.duong.springdemoresful.model.Comment;
import com.duong.springdemoresful.model.Post;
import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.repository.CommentRepository;
import com.duong.springdemoresful.service.specification.CommentSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;

    private Comment toEntity(CommentRequest requestDto){
        Comment comment = new Comment();
        comment.setId(requestDto.getId());
        comment.setContent(requestDto.getContent());
        User user = new User();
        user.setId(requestDto.getUser().getId());
        Post post = new Post();
        post.setId(requestDto.getPost().getId());
        comment.setUser(user);
        comment.setPost(post);
        return comment;

    }

    private CommentResponse toDto(Comment comment){
        CommentResponse.OutputUser user = new CommentResponse.OutputUser();
        user.setId(comment.getUser().getId());
        CommentResponse.OutputPost post = new CommentResponse.OutputPost();
        post.setId(comment.getPost().getId());
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .user(user)
                .post(post)
                .build();
    }

    public Page<CommentResponse> getAllComments(Pageable pageable, CommentFilterRequest filterRequest){
        Specification<Comment> specification = Specification.allOf(
                CommentSpecification.hasComment(filterRequest),
                CommentSpecification.hasPost(filterRequest),
                CommentSpecification.hasUser(filterRequest)
        );
        return repository.findAll(specification,pageable).map(this::toDto);
    }

    public CommentResponse getCommentById(Long id){
        return toDto(repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Comment not found")));
    }

    public CommentResponse createComment(CommentRequest requestDto){
        return toDto(repository.save(toEntity(requestDto)));
    }

    public void deleteCommentById(Long id){
        Comment comment = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment not found"));
        repository.delete(comment);
    }
    @Transactional
    public CommentResponse updateCommentById(Long id, CommentRequest requestDto){
        Comment currentComment = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment not found"));
        currentComment.setContent(requestDto.getContent());
        User user = new User();
        user.setId(requestDto.getUser().getId());
        Post post = new Post();
        post.setId(requestDto.getPost().getId());
        currentComment.setUser(user);
        currentComment.setPost(post);
        return toDto(currentComment);

    }

}
