package com.example.realsoft.card_service.service.imp;

import com.example.realsoft.card_service.entity.Card;
import com.example.realsoft.card_service.exception.CardNotFound;
import com.example.realsoft.card_service.model.CardDto;
import com.example.realsoft.card_service.repository.CardRepository;
import com.example.realsoft.card_service.service.CardService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class CardServiceImp implements CardService {

    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    @Override
    public CardDto createCard(CardDto cardDto) {
        Card card = new Card();
        card.setTitle(cardDto.getTitle());
        card.setListId(cardDto.getListId());
        card.setCreatedAt(LocalDate.now());
        card.setDescription(cardDto.getDescription());
        cardRepository.save(card);
        return modelMapper.map(card, CardDto.class);
    }

    @Override
    public CardDto getCardById(Long cardId) throws CardNotFound {
        return modelMapper.map(findCard(cardId), CardDto.class);
    }

    @Override
    public CardDto editCard(Long cardId, CardDto cardDto) throws CardNotFound {
        Card card = findCard(cardId);
        card.setTitle(cardDto.getTitle());
        card.setListId(cardDto.getListId());
        card.setDescription(cardDto.getDescription());
        cardRepository.save(card);
        return modelMapper.map(card, CardDto.class);
    }

    @Override
    public void deleteCard(Long cardId) throws CardNotFound {
        cardRepository.delete(findCard(cardId));
    }

    private Card findCard(Long cardId) throws CardNotFound {
        return cardRepository.findById(cardId).orElseThrow(() ->
                new CardNotFound("Id", cardId));
    }
}
