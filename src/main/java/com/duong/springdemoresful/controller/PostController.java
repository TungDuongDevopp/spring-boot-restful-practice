package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.dto.request.PostFilterRequest;
import com.duong.springdemoresful.dto.request.PostRequest;
import com.duong.springdemoresful.dto.response.PostResponse;
import com.duong.springdemoresful.helper.ApiResponse;
import com.duong.springdemoresful.helper.PageResponse;
import com.duong.springdemoresful.model.Post;
import com.duong.springdemoresful.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<ApiResponse<PageResponse<PostResponse>>> getPosts(Pageable pageable, PostFilterRequest filterRequest){
        Page<PostResponse> posts= service.getAllPost(pageable,filterRequest);
        return ApiResponse.success(PageResponse.from(posts));
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
