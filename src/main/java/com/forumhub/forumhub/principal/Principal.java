package com.forumhub.forumhub.principal;

import com.forumhub.forumhub.dto.DadosCadastroTopico;
import com.forumhub.forumhub.dto.DadosListagemTopico;
import com.forumhub.forumhub.model.Curso;
import com.forumhub.forumhub.model.Topico;
import com.forumhub.forumhub.model.Usuario;
import com.forumhub.forumhub.repository.CursoRepository;
import com.forumhub.forumhub.repository.TopicoRepository;
import com.forumhub.forumhub.repository.UsuarioRepository;
import com.forumhub.forumhub.service.TopicoService;

import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private final Scanner scanner = new Scanner(System.in);
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final TopicoService topicoService;

    public Principal(TopicoRepository tRepository, UsuarioRepository uRepository, CursoRepository cRepository, TopicoService tService) {
        this.topicoRepository = tRepository;
        this.usuarioRepository = uRepository;
        this.cursoRepository = cRepository;
        this.topicoService = tService;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    \n
                    ╔════════════════════════════════════════╗
                    ║            FORUMHUB API                ║
                    ╠════════════════════════════════════════╣
                    ║ 1 - Cadastrar novo tópico              ║
                    ║ 2 - Listar todos os tópicos            ║
                    ║ 3 - Detalhar um tópico por ID          ║
                    ║ 4 - Atualizar um tópico                ║
                    ║ 5 - Excluir um tópico                  ║
                    ║                                        ║
                    ║ 0 - Sair                               ║
                    ╚════════════════════════════════════════╝
                    Escolha uma opção: """;
            System.out.print(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarTopico();
                    break;
                case 2:
                    listarTopicos();
                    break;
                case 3:
                    detalharTopico();
                    break;
                case 4:
                    atualizarTopico();
                    break;
                case 5:
                    excluirTopico();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarTopico() {
        System.out.println("--- Cadastro de Novo Tópico ---");
        System.out.print("Título: ");
        var titulo = scanner.nextLine();
        System.out.print("Mensagem: ");
        var mensagem = scanner.nextLine();
        System.out.print("ID do Autor: ");
        var autorId = scanner.nextLong();
        System.out.print("ID do Curso: ");
        var cursoId = scanner.nextLong();
        scanner.nextLine();

        DadosCadastroTopico dados = new DadosCadastroTopico(titulo, mensagem, autorId, cursoId);

        Usuario autor = usuarioRepository.getReferenceById(dados.autorId());
        Curso curso = cursoRepository.getReferenceById(dados.cursoId());

        Topico topico = new Topico(dados, autor, curso);

        topicoRepository.save(topico);
        System.out.println("Tópico cadastrado com sucesso! ID: " + topico.getId());
    }

    private void listarTopicos() {
        var topicos = topicoService.listarTodos();
        if (topicos.isEmpty()) {
            System.out.println("\nNenhum tópico ativo encontrado.");
        } else {
            System.out.println("\n--- LISTA DE TÓPICOS ATIVOS ---");
            topicos.forEach(System.out::println);
        }
    }

    // Na sua classe Principal.java
    private void detalharTopico() {
        System.out.print("Digite o ID do tópico para ver os detalhes: ");
        var idTopico = scanner.nextLong();
        scanner.nextLine();

        try {
            var dadosTopico = topicoService.detalharTopico(idTopico);
            System.out.println("\n--- DETALHES DO TÓPICO ID: " + idTopico + " ---");
            System.out.println(dadosTopico);
            System.out.println("------------------------------------");
        } catch (Exception e) { // Pega o EntityNotFoundException lançado pelo getReferenceById
            System.out.println("\nErro: Tópico com ID " + idTopico + " não encontrado.");
        }
    }

    private void atualizarTopico() {
        System.out.print("Digite o ID do tópico que deseja atualizar: ");
        var idTopico = scanner.nextLong();
        scanner.nextLine();

        var topicoOptional = topicoRepository.findById(idTopico);

        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            System.out.println("Encontrado o tópico: " + topico.getTitulo());
            System.out.print("Digite o novo título (ou deixe em branco para não alterar): ");
            var novoTitulo = scanner.nextLine();
            System.out.print("Digite a nova mensagem (ou deixe em branco para não alterar): ");
            var novaMensagem = scanner.nextLine();

            if (novoTitulo != null && !novoTitulo.trim().isEmpty()) {
                topico.setTitulo(novoTitulo);
            }
            if (novaMensagem != null && !novaMensagem.trim().isEmpty()) {
                topico.setMensagem(novaMensagem);
            }

            topicoRepository.save(topico);
            System.out.println("Tópico atualizado com sucesso!");
        } else {
            System.out.println("Tópico com ID " + idTopico + " não encontrado.");
        }
    }

    private void excluirTopico() {
        System.out.print("Digite o ID do tópico que deseja excluir (marcar como inativo): ");
        var idTopico = scanner.nextLong();
        scanner.nextLine();

        Optional<Topico> topicoOptional = topicoRepository.findById(idTopico);

        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            topico.excluir();
            topicoRepository.save(topico);
            System.out.println("Tópico com ID " + idTopico + " foi marcado como inativo.");
        } else {
            System.out.println("Tópico com ID " + idTopico + " não encontrado.");
        }
    }
}