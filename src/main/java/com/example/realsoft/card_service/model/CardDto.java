package com.example.realsoft.card_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CardDto {
    private Long cardId;
    private String title;
    private Long listId;
    private String description;
    private LocalDate createdAt;
}
