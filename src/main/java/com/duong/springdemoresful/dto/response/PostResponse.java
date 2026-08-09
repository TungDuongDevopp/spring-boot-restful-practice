package com.duong.springdemoresful.dto.response;

import lombok.*;
import org.hibernate.sql.Update;

import java.time.Instant;
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

    private Instant createdAt;

    private Instant updatedAt;

    private String authorName;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OutputTag{
        private Long id;
        private String name;

    }
}
