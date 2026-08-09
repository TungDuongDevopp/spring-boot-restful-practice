package com.duong.springdemoresful.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@Getter @Setter
public class CommentResponse {
    private Long id;

    private String content;

    private OutputUser user;


    private OutputPost post;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class OutputUser{
        private Long id;
        private String userName;
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class OutputPost{
        private Long id;
        private String postName;
    }
}
