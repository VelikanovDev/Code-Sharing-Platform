package com.velikanovdev.platform.repository;

import com.velikanovdev.platform.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
