package com.duong.springdemoresful.dto.response;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {

    private Long id;

    private String content;

    private String title;

    private List<OutputTag> tags;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OutputTag{
        private Long id;
        private String name;

    }
}
