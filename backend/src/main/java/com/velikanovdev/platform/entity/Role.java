package com.velikanovdev.platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private String name;

    @OneToMany(mappedBy = "role")
    @ToString.Exclude
    @JsonIgnore
    private List<User> users;

    public Role(String name) {
        this.name = name;
    }
}
