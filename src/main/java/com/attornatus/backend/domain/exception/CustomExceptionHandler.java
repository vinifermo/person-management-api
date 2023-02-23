package com.attornatus.backend.domain.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String PESSOA_NAO_ENCONTRADA = "Pessoa não encontrada!";
    public static final String ENDERECO_NAO_ENCONTRADO = "Endereço não encontrado!";

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseError handle(EntityNotFoundException ex) {
        ex.printStackTrace();

        return new ResponseError(ex.getMessage());
    }
}
