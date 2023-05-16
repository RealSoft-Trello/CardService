package com.example.realsoft.card_service.controller;

import com.example.realsoft.card_service.exception.CardNotFound;
import com.example.realsoft.card_service.model.CardDto;
import com.example.realsoft.card_service.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("realsoft/trello/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardDto> createCard(@RequestBody CardDto cardDto) {
        return new ResponseEntity<>(cardService.createCard(cardDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> getCard(@PathVariable(name = "id") Long cardId) throws CardNotFound {
        return ResponseEntity.ok(cardService.getCardById(cardId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardDto> editCard(@PathVariable(name = "id") Long cardId, @RequestBody CardDto cardDto) throws CardNotFound {
        return ResponseEntity.ok(cardService.editCard(cardId, cardDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable(name = "id") Long cardId) throws CardNotFound {
        cardService.deleteCard(cardId);
        return ResponseEntity.ok("Card deleted successfully!");
    }
}
