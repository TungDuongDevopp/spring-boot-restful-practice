package com.duong.springdemoresful.dto;

import com.duong.springdemoresful.model.Comment;
import com.duong.springdemoresful.model.Post;
import com.duong.springdemoresful.model.Role;
import lombok.*;

import java.util.List;
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class UserResponseDto {

    private Long id;

    private String name;

    private String email;

    private String address;

    private Role role;

    private List<Comment> comments;

    private List<Post> posts;

}
