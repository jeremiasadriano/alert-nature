package com.jeremias.beprepared.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String phone;
    private String deviceId;
    private boolean verified;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
