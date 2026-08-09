package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.dto.request.CommentFilterRequest;
import com.duong.springdemoresful.dto.request.CommentRequest;
import com.duong.springdemoresful.dto.response.CommentResponse;
import com.duong.springdemoresful.helper.ApiResponse;
import com.duong.springdemoresful.helper.PageResponse;
import com.duong.springdemoresful.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;

    @PostMapping("/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(@Valid @RequestBody CommentRequest requestDto){
        return ApiResponse.success(service.createComment(requestDto));
    }

    @GetMapping("/comments")
    public ResponseEntity<ApiResponse<PageResponse<CommentResponse>>> getComments(Pageable pageable, CommentFilterRequest filterRequest){
        Page<CommentResponse> pages = service.getAllComments(pageable,filterRequest);
        return ApiResponse.success(PageResponse.from(pages));
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<ApiResponse<CommentResponse>> getCommentById(@PathVariable Long id){
        return ApiResponse.success(service.getCommentById(id));
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(@PathVariable Long id, @Valid @RequestBody CommentRequest requestDto){
        return ApiResponse.success(service.updateCommentById(id,requestDto));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<ApiResponse<Void>> updateComment(@PathVariable Long id){
        service.deleteCommentById(id);
        return ApiResponse.success(null,"Deleted Successfully");
    }

}
