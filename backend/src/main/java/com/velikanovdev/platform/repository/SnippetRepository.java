package com.velikanovdev.platform.repository;

import com.velikanovdev.platform.entity.Comment;
import com.velikanovdev.platform.entity.Rating;
import com.velikanovdev.platform.entity.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SnippetRepository extends JpaRepository<Snippet, Long> {
    List<Snippet> findAllByOrderByDateDesc();

    @Query("SELECT c FROM Snippet s JOIN s.comments c WHERE c.username = :username")
    List<Comment> findAllCommentsByUsername(String username);

    @Query("SELECT r FROM Snippet s JOIN s.ratings r WHERE r.username = :username")
    List<Rating> findAllRatingsByUsername(String username);
}
