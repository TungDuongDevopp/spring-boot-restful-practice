package com.duong.springdemoresful.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    private Long id;

    @NotBlank(message = "content không được để trống")
    private String content;

    @NotNull(message = "user không được để trống")
    @Valid
    private InputUser user;

    @NotNull(message = "post không được để trống")
    @Valid
    private InputPost post;

    @Getter @Setter
    @NoArgsConstructor
    public static class InputUser{
        @NotNull(message = "user.id không được để trống")
        private Long id;
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class InputPost{
        @NotNull(message = "post.id không được để trống")
        private Long id;
    }

}
