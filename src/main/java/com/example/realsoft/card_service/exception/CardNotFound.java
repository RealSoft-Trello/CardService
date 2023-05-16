package com.example.realsoft.card_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class CardNotFound extends Exception {
    private final String fieldName;
    private final Long fieldValue;

    public CardNotFound(String fieldName, Long fieldValue) {
        super(String.format("Card Not Found with %s : %s", fieldName, fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
