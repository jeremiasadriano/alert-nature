package com.jeremias.beprepared.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "User_account")
@SequenceGenerator(sequenceName = "account_seq", name = "account_gen", initialValue = 1, allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsersAccountInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_gen")
    private Long id;
    @Column(unique = true)
    private String token;
    private LocalDateTime expirationToken;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;
}
