package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.dto.request.PostRequest;
import com.duong.springdemoresful.dto.response.PostResponse;
import com.duong.springdemoresful.helper.ApiResponse;
import com.duong.springdemoresful.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    @PostMapping("/posts")
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@Valid @RequestBody PostRequest requestDto){
        return ApiResponse.success(service.creatPost(requestDto));
    }

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPosts(){
        return ApiResponse.success(service.getAllPost());
    }
    @GetMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getPostById(@PathVariable Long id){
        return ApiResponse.success(service.getPostById(id));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(@PathVariable Long id , @Valid @RequestBody PostRequest updatePost){
        return ApiResponse.success(service.updatePostById(id,updatePost));
    }
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id){
        service.deletePostById(id);
        return ApiResponse.success(null,"Deleted successful");
    }
}
