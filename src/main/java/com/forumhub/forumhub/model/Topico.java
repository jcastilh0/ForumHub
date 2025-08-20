package com.forumhub.forumhub.model;

import com.forumhub.forumhub.dto.DadosAtualizacaoTopico;
import com.forumhub.forumhub.dto.DadosCadastroTopico;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@Setter // Adicione o @Setter para os métodos de atualização
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private Boolean status = true; // Alterado para Boolean por boa prática

    // --- CORREÇÃO AQUI ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id") // Aponta para a coluna autor_id na tabela topicos
    private Usuario autor;

    // --- E AQUI ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id") // Aponta para a coluna curso_id na tabela topicos
    private Curso curso;

    public Topico(DadosCadastroTopico dados, Usuario autor, Curso curso) {
        this.status = true;
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.autor = autor;
        this.curso = curso;
        this.dataCriacao = LocalDateTime.now();
    }

    public void atualizarInformacoes(DadosAtualizacaoTopico dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
    }

    public void excluir() {
        this.status = false;
    }
}