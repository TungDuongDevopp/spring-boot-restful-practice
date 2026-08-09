package com.duong.springdemoresful.service;

import com.duong.springdemoresful.dto.request.PostFilterRequest;
import com.duong.springdemoresful.dto.request.PostRequest;

import com.duong.springdemoresful.dto.response.PostResponse;
import com.duong.springdemoresful.helper.exception.ResourceNotFoundException;
import com.duong.springdemoresful.model.Post;
import com.duong.springdemoresful.model.Tag;
import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.repository.PostRepository;
import com.duong.springdemoresful.service.specification.PostSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;

    private Post convertDtoToPost(PostRequest requestDto){
        Post postEntity = new Post();
        postEntity.setId(requestDto.getId());
        postEntity.setTitle(requestDto.getTitle());
        postEntity.setContent(requestDto.getContent());

        List<Tag> tags = requestDto.getTags().stream()
                .map(inputTag -> new Tag(inputTag.getId(),inputTag.getName(),null)).collect(Collectors.toList());
        postEntity.setTags(tags);

        User user = new User();
        user.setId(requestDto.getUser().getId());
        postEntity.setUser(user);
        return postEntity;

    }

    private PostResponse convertToDto(Post post){

        List<PostResponse.OutputTag> tags = post.getTags().stream()
                .map( tagEntity-> new PostResponse.OutputTag(tagEntity.getId(),tagEntity.getName()))
                .toList();
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .tags(tags)
                .build();
    }

    public PostResponse creatPost(PostRequest postRequest){
        return convertToDto(repository.save(convertDtoToPost(postRequest)));
    }

    public Page<PostResponse> getAllPost(Pageable page, PostFilterRequest filterRequest){
        Specification<Post> specification = Specification.allOf(
                PostSpecification.hasTitle(filterRequest),
                PostSpecification.hasTag(filterRequest),
                PostSpecification.hasUser(filterRequest),
                PostSpecification.updatedAtBetween(filterRequest),
                PostSpecification.createdAtBetween(filterRequest)
                );
      return repository.findAll(specification,page).map(this::convertToDto);
    }

    public PostResponse getPostById(Long id){
        return  convertToDto(repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post not found")
        ));
    }
    @Transactional
    public PostResponse updatePostById(Long id, PostRequest updatePost){
        Post currentPost = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post not found")
        );
        currentPost.setTitle(updatePost.getTitle());
        currentPost.setContent(updatePost.getContent());
        if(!updatePost.getTags().isEmpty()){
            List<Tag> tags = updatePost.getTags().stream()
                    .map(tagDto-> new Tag(tagDto.getId(),tagDto.getName(),null)).toList();
            currentPost.setTags(tags);
        }
        return convertToDto(currentPost);
    }
    public void deletePostById(Long id){
        Post post = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post not found"));
        repository.delete(post);
    }
}
