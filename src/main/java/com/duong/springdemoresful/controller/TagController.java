package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.helper.ApiResponse;
import com.duong.springdemoresful.model.Tag;
import com.duong.springdemoresful.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TagController {

    private final TagService service;

    @PostMapping("/tags")
    public ResponseEntity<ApiResponse<Tag>> createTag(@Valid @RequestBody Tag tag){
        Tag createTag = service.saveTag(tag);
        return ApiResponse.created(createTag);

    }
    @GetMapping("/tags")
    public ResponseEntity<ApiResponse<List<Tag>>> getAllTags(){
        List<Tag> tags = service.getAllTags();
        return ApiResponse.success(tags);
    }

    @GetMapping("/tags/{id}")
    public  ResponseEntity<ApiResponse<Tag>> getTagById(@PathVariable Long id){
        Tag tag = service.getTagById(id);
        return ApiResponse.success(tag);
    }

    @PutMapping("/tags/{id}")
    public ResponseEntity<ApiResponse<Tag>> updateTag(@PathVariable Long id, @Valid @RequestBody Tag tag){

        Tag updateTag = service.updateTag(tag,id);
        return ApiResponse.success(updateTag);

    }
    @DeleteMapping("/tags/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTag(@PathVariable Long id){
        service.deleteTagById(id);
        return ApiResponse.success(null, "Deleted successfully!");
    }
}
