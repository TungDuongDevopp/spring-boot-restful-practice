package com.duong.springdemoresful.service;

import com.duong.springdemoresful.helper.exception.DuplicateResourceException;
import com.duong.springdemoresful.helper.exception.ResourceNotFoundException;
import com.duong.springdemoresful.model.Post;
import com.duong.springdemoresful.model.Tag;
import com.duong.springdemoresful.repository.PostRepository;
import com.duong.springdemoresful.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {
    final TagRepository repository;
    final PostRepository postRepository;

    public Page<Tag> getAllTags(Pageable pageable){
        return  repository.findAll(pageable).map(tag -> new Tag(tag.getId(),tag.getName(),null));
    }

    public Tag getTagById(Long id){
        return repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Tag not found"));
    }
    public Tag saveTag(Tag tag){
        if(repository.existsByName(tag.getName())){
            throw new DuplicateResourceException("Tag already exists");
        }
        return repository.save(tag);
    }

    @Transactional
    public Tag updateTag(Tag updateTag,Long id){

        if(repository.existsByName(updateTag.getName())){
            throw new DuplicateResourceException("Tag already exists");
        }
        Tag currentTag = getTagById(id);
        currentTag.setName(updateTag.getName());
       return currentTag;

    }

    public void deleteTagById(Long id){
        Tag tag = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));

        List<Post> posts = postRepository.findByTagsContains(tag);
        for(Post post : posts){
            post.getTags().remove(tag);
            postRepository.save(post);
        }

        repository.delete(tag);
    }
}
