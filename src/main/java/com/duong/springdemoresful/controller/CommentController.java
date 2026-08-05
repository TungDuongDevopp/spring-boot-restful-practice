package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.dto.request.CommentRequestDto;
import com.duong.springdemoresful.dto.response.CommentResponseDto;
import com.duong.springdemoresful.helper.ApiResponse;
import com.duong.springdemoresful.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;

    @PostMapping("/comments")
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(@Valid @RequestBody CommentRequestDto requestDto){
        return ApiResponse.success(service.createComment(requestDto));
    }

    @GetMapping("/comments")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> getAllComments(){
        return ApiResponse.success(service.getAllComments());
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> getCommentById(@PathVariable Long id){
        return ApiResponse.success(service.getCommentById(id));
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(@PathVariable Long id,@Valid @RequestBody CommentRequestDto requestDto){
        return ApiResponse.success(service.updateCommentById(id,requestDto));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<ApiResponse<Void>> updateComment(@PathVariable Long id){
        service.deleteCommentById(id);
        return ApiResponse.success(null,"Deleted Successfully");
    }

}
