package com.douglas.hub.dto.email;

public record EmailRequestDTO(
    String destinatario,
    String assunto,
    String mensagem
) {}
