package com.forumhub.forumhub.dto;

import com.forumhub.forumhub.model.Topico;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // Import para formatar a data

public record DadosListagemTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Boolean status,
        String autor,
        String curso) {

    public DadosListagemTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome()
        );
    }

    // *** MÉTODO NOVO AQUI ***
    @Override
    public String toString() {
        // Formata a data para um padrão mais legível
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataFormatada = dataCriacao.format(formatter);
        String statusFormatado = status ? "Aberto" : "Fechado";

        return """
                --- Tópico ---
                ID: %d
                Título: %s
                Mensagem: %s
                Data de Criação: %s
                Status: %s
                Autor: %s
                Curso: %s
                ----------------
                """.formatted(id, titulo, mensagem, dataFormatada, statusFormatado, autor, curso);
    }
}