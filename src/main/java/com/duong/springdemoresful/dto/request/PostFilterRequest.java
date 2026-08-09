package com.duong.springdemoresful.dto.request;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostFilterRequest {

    private String title;

    private Long userId;

    private List<String> tags;

    private Instant fromCreatedAt;

    private Instant toCreatedAt;

    private Instant fromUpdatedAt;

    private Instant toUpdatedAt;

}
