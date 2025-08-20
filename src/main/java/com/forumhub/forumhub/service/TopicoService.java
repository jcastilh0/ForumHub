package com.forumhub.forumhub.service;

import com.forumhub.forumhub.dto.DadosListagemTopico; // Importe se necessário
import jakarta.persistence.EntityNotFoundException;
import com.forumhub.forumhub.dto.DadosListagemTopico;
import com.forumhub.forumhub.model.Topico;
import com.forumhub.forumhub.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository repository;

    @Transactional(readOnly = true)
    public List<DadosListagemTopico> listarTodos() {
        return repository.findAllByStatusTrue()
                .stream()
                .map(DadosListagemTopico::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DadosListagemTopico detalharTopico(Long id) {
        var topico = repository.getReferenceById(id);
        return new DadosListagemTopico(topico);
    }
}