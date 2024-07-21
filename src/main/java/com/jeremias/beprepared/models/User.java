package com.jeremias.beprepared.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "Usuarios")
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(sequenceName = "usuarios_seq", name = "usuarios_gen", allocationSize = 1, initialValue = 1)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_gen")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
}
