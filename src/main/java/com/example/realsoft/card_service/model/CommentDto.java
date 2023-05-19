package com.example.realsoft.card_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDto {
    private Long commentId;
    private Long userId;
    private Long cardId;
    private String content;
}
