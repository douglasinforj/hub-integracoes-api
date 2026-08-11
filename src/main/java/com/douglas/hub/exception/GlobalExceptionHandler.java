package com.douglas.hub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;

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

    @ExceptionHandler(FeignException.NotFound.class)
    public ProblemDetail handleFeignNotFound(FeignException.NotFound ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Recurso não encontrado");
        problem.setDetail("Usuário ou recurso não encontrado no GitHub");
        return problem;
    }

    @ExceptionHandler(FeignException.class)
    public ProblemDetail handleFeignGeneric(FeignException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);
        problem.setTitle("Erro na integração com GitHub");
        problem.setDetail("GitHub API retornou: " + ex.status());
        return problem;
    }



}
