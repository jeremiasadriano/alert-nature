package com.jeremias.beprepared.models;

import com.jeremias.beprepared.models.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "Usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Enumerated(EnumType.STRING)
    private Roles roles;
    @Column(name = "status")
    private boolean active = false;
}
