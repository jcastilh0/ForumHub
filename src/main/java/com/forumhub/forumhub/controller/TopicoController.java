package com.forumhub.forumhub.controller;

import com.forumhub.forumhub.dto.DadosAtualizacaoTopico;
import com.forumhub.forumhub.dto.DadosCadastroTopico;
import com.forumhub.forumhub.dto.DadosListagemTopico;
import com.forumhub.forumhub.model.Curso;
import com.forumhub.forumhub.model.Topico;
import com.forumhub.forumhub.model.Usuario;
import com.forumhub.forumhub.repository.CursoRepository;
import com.forumhub.forumhub.repository.TopicoRepository;
import com.forumhub.forumhub.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados) {
        Usuario autor = usuarioRepository.getReferenceById(dados.autorId());
        Curso curso = cursoRepository.getReferenceById(dados.cursoId());

        Topico topico = new Topico(dados, autor, curso);


        topicoRepository.save(topico);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemTopico>> listar() {
        List<Topico> topicos = topicoRepository.findAll();

        List<DadosListagemTopico> dadosListagem = topicos.stream()
                .map(DadosListagemTopico::new)
                .toList();

        return ResponseEntity.ok(dadosListagem);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosListagemTopico(topico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados) {
        var topico = topicoRepository.getReferenceById(dados.id());

        topico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosListagemTopico(topico));
    }
}