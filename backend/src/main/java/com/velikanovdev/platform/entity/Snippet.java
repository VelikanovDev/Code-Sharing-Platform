package com.velikanovdev.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "snippets")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Snippet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String code;

    @Column
    private LocalDateTime date;

    @Column
    private LocalDateTime editDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "snippet", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "snippet", cascade = CascadeType.ALL)
    private List<Rating> ratings;
}
