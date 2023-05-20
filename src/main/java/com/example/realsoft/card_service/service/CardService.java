package com.example.realsoft.card_service.service;

import com.example.realsoft.card_service.exception.CardNotFound;
import com.example.realsoft.card_service.model.CardDto;
import com.example.realsoft.card_service.model.CommentDto;

import java.util.List;

public interface CardService {
    CardDto createCard(CardDto cardDto);
    CardDto getCardById(Long cardId) throws CardNotFound;
    CardDto editCard(Long cardId, CardDto cardDto) throws CardNotFound;
    void deleteCard(Long cardId) throws CardNotFound;

    List<CardDto> getCardsByList(Long listId);
    List<CommentDto> getCommentsByCard(Long cardId) throws CardNotFound;
    void assignCardToUser(Long cardId, Long userId) throws CardNotFound;
}
