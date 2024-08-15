package com.jeremias.beprepared.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "Citizens")
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(sequenceName = "Citizens_seq", name = "Citizens_gen", allocationSize = 1, initialValue = 1)
@Entity
public class Citizens {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Citizens_gen")
    private Long id;
    private String name;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String email;
    @Column(length = 6)
    private String otp;
    private LocalDateTime otpDuration;
    private boolean verified;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @PrePersist
    private void optDurationTime() {
        this.setOtpDuration(LocalDateTime.now().plusMinutes(10L));
    }
}
