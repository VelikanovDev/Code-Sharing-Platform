package com.velikanovdev.platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int value; // Assuming ratings are integers (e.g., 1-5)

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user who provided the rating

    @ManyToOne
    @JoinColumn(name = "snippet_id", nullable = false)
    private Snippet snippet; // The snippet that was rated

    @Column
    private LocalDateTime date;
}
