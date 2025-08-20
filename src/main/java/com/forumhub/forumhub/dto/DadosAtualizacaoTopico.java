package com.forumhub.forumhub.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTopico(
        @NotNull
        Long id,
        String titulo,
        String mensagem) {
}