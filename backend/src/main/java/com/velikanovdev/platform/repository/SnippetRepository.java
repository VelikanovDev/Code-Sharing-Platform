package com.velikanovdev.platform.repository;

import com.velikanovdev.platform.entity.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnippetRepository extends JpaRepository<Snippet, Long> {
    List<Snippet> findAllByOrderByDateDesc();
}
