package com.forumhub.forumhub;

import com.forumhub.forumhub.principal.Principal;
import com.forumhub.forumhub.repository.CursoRepository;
import com.forumhub.forumhub.repository.TopicoRepository;
import com.forumhub.forumhub.repository.UsuarioRepository;
import com.forumhub.forumhub.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForumhubApplication implements CommandLineRunner {

	@Autowired
	private TopicoRepository topicoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private CursoRepository cursoRepository;
	@Autowired
	private TopicoService topicoService;

	public static void main(String[] args) {
		SpringApplication.run(ForumhubApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}