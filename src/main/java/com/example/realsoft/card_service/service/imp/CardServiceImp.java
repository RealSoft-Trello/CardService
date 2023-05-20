package com.example.realsoft.card_service.service.imp;

import com.example.realsoft.card_service.entity.Card;
import com.example.realsoft.card_service.exception.CardNotFound;
import com.example.realsoft.card_service.model.CardAssign;
import com.example.realsoft.card_service.model.CardDto;
import com.example.realsoft.card_service.model.CommentDto;
import com.example.realsoft.card_service.repository.CardRepository;
import com.example.realsoft.card_service.service.CardService;
import com.example.realsoft.card_service.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CardServiceImp implements CardService {

    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;

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

    @Override
    public List<CardDto> getCardsByList(Long listId) {
        return cardRepository.getCardsByListId(listId).stream()
                .map(card -> modelMapper.map(card, CardDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getCommentsByCard(Long cardId) throws CardNotFound {
        Card card = cardRepository.findById(cardId).orElse(null);
        if (card == null) {
            throw new CardNotFound("Id", cardId);
        }

        String commentsUrl = "http://comment-service/realsoft/trello/comments/card/" + cardId;
        ResponseEntity<List<CommentDto>> response = restTemplate.exchange(
                commentsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CommentDto>>() {}
        );
        return response.getBody();
    }

    @Override
    public void assignCardToUser(Long cardId, Long userId) throws CardNotFound {
        Card card = cardRepository.findById(cardId).orElse(null);
        if (card == null) {
            throw new CardNotFound("ID", cardId);
        }
        CardAssign cardAssign = new CardAssign(cardId, userId, card.getTitle());
        rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.ROUTING_KEY, cardAssign);
    }

    private Card findCard(Long cardId) throws CardNotFound {
        return cardRepository.findById(cardId).orElseThrow(() ->
                new CardNotFound("Id", cardId));
    }
}
