package com.douglas.hub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CepNotFoundException.class)   // exception personalizado criada
    public ProblemDetail handleCepNotFound(CepNotFoundException ex) {
        // ProblemDetail é o padrão moderno do Spring Boot 3 (RFC 7807)
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("CEP não encontrado");
        problem.setDetail(ex.getMessage());
        return problem;
    }


}
