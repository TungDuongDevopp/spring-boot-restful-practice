package com.duong.springdemoresful.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;

    private String content;

    private String title;

    private List<OutputTag> tags;

    @Getter @Setter
    @NoArgsConstructor
    public static class OutputTag{
        private Long id;
        private String name;

    }
}
