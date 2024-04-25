package com.velikanovdev.platform.repository;

import com.velikanovdev.platform.entity.Rating;
import com.velikanovdev.platform.entity.Snippet;
import com.velikanovdev.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByUserAndSnippet(User user, Snippet snippet);
}
