package com.duong.springdemoresful.service;

import com.duong.springdemoresful.dto.request.PostRequestDto;

import com.duong.springdemoresful.dto.response.PostResponseDto;
import com.duong.springdemoresful.helper.ResourceNotFoundException;
import com.duong.springdemoresful.model.Post;
import com.duong.springdemoresful.model.Tag;
import com.duong.springdemoresful.model.User;
import com.duong.springdemoresful.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;

    private Post convertDtoToPost(PostRequestDto requestDto){
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

    private PostResponseDto convertToDto(Post post){

        List<PostResponseDto.OutputTag> tags = post.getTags().stream()
                .map( tagEntity-> new PostResponseDto.OutputTag(tagEntity.getId(),tagEntity.getName()))
                .toList();
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .tags(tags)
                .build();
    }

    public PostResponseDto creatPost(PostRequestDto postRequestDto){
        return convertToDto(repository.save(convertDtoToPost(postRequestDto)));
    }

    public List<PostResponseDto> getAllPost(){
      return repository.findAll().stream().map(this::convertToDto).toList();
    }

    public PostResponseDto getPostById(Long id){
        return  convertToDto(repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post not found")
        ));
    }
    @Transactional
    public PostResponseDto updatePostById(Long id,PostRequestDto updatePost){
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
