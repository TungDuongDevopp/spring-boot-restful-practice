package com.duong.springdemoresful.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentFilterRequest {
    private String comment;

    private Long postId;

    private Long userId;
}
