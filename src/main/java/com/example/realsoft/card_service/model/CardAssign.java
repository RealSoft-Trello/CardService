package com.example.realsoft.card_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardAssign {
    private Long cardId;
    private Long userId;
    private String title;
}
