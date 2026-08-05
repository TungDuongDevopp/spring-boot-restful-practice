package com.duong.springdemoresful.service;

import com.duong.springdemoresful.dto.request.CommentRequestDto;
import com.duong.springdemoresful.dto.response.CommentResponseDto;
import com.duong.springdemoresful.helper.ResourceNotFoundException;
import com.duong.springdemoresful.model.Comment;
import com.duong.springdemoresful.model.Post;
import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;

    private Comment toEntity(CommentRequestDto requestDto){
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

    private CommentResponseDto toDto(Comment comment){
        CommentResponseDto.OutputUser user = new CommentResponseDto.OutputUser();
        user.setId(comment.getUser().getId());
        CommentResponseDto.OutputPost post = new CommentResponseDto.OutputPost();
        post.setId(comment.getPost().getId());
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .user(user)
                .post(post)
                .build();
    }

    public List<CommentResponseDto> getAllComments(){
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public CommentResponseDto getCommentById(Long id){
        return toDto(repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Comment not found")));
    }

    public CommentResponseDto createComment(CommentRequestDto requestDto){
        return toDto(repository.save(toEntity(requestDto)));
    }

    public void deleteCommentById(Long id){
        Comment comment = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment not found"));
        repository.delete(comment);
    }
    @Transactional
    public CommentResponseDto updateCommentById(Long id,CommentRequestDto requestDto){
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
