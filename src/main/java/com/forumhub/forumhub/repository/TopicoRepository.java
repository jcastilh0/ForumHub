package com.forumhub.forumhub.repository;

import com.forumhub.forumhub.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    List<Topico> findAllByStatusTrue();
}
