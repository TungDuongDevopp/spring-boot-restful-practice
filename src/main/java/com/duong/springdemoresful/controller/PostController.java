package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.dto.request.PostRequestDto;
import com.duong.springdemoresful.dto.response.PostResponseDto;
import com.duong.springdemoresful.helper.ApiResponse;
import com.duong.springdemoresful.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    @PostMapping("/posts")
    public ResponseEntity<ApiResponse<PostResponseDto>> createPost(@Valid @RequestBody PostRequestDto requestDto){
        return ApiResponse.success(service.creatPost(requestDto));
    }

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<List<PostResponseDto>>> getPosts(){
        return ApiResponse.success(service.getAllPost());
    }
    @GetMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> getPostById(@PathVariable Long id){
        return ApiResponse.success(service.getPostById(id));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> updatePost(@PathVariable Long id ,@Valid @RequestBody PostRequestDto  updatePost){
        return ApiResponse.success(service.updatePostById(id,updatePost));
    }
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id){
        service.deletePostById(id);
        return ApiResponse.success(null,"Deleted successful");
    }
}
