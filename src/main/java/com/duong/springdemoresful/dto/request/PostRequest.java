package com.duong.springdemoresful.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class PostRequest {

    private Long id;
    @NotBlank(message = "title không được để trống")
    private String title;

    @NotBlank(message = "content không được để trống")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    @NotNull(message = "user không được để trống")
    @Valid
    private InputUser user;

    @NotNull(message = "Tag không được để trống")
    @Valid
    private List<InputTag> tags;

    @Getter @Setter
    @NoArgsConstructor
    public static class InputTag{
        @NotNull(message = "tag.id không được để trống")
        private Long id;

        @NotBlank(message = "tag.name không được để trống")
        private String name;
    }
    @Getter @Setter
    @NoArgsConstructor
    public static class InputUser{
        @NotNull(message = "user.id không được để trống")
        private Long id;
    }
}
