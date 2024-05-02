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
    private Integer ratingValue; // Assuming ratings are integers (e.g., 0-5)

    @Column
    private String username;

    @ManyToOne
    @JoinColumn(name = "snippet_id", nullable = false)
    private Snippet snippet; // The snippet that was rated

    @Column
    private LocalDateTime date;
}
