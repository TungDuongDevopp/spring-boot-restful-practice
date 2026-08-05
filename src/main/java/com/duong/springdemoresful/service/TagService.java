package com.duong.springdemoresful.service;

import com.duong.springdemoresful.helper.DuplicateResourceException;
import com.duong.springdemoresful.helper.ResourceNotFoundException;
import com.duong.springdemoresful.model.Tag;
import com.duong.springdemoresful.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {
    final TagRepository repository;

    public List<Tag> getAllTags(){
        return  repository.findAll();
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

    public Tag updateTag(Tag updateTag,Long id){

        if(repository.existsByName(updateTag.getName())){
            throw new DuplicateResourceException("Tag already exists");
        }
        Tag currentTag = getTagById(id);
        currentTag.setName(updateTag.getName());
       return repository.save(currentTag);

    }

    public void deleteTagById(Long id){
        Tag tag = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));

        repository.delete(tag);
    }
}
