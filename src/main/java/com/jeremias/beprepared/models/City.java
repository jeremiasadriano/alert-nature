package com.jeremias.beprepared.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@SequenceGenerator(sequenceName = "distritos_seq", name = "distritos_gen", allocationSize = 1, initialValue = 1)
@Table(name = "distritos")
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "distritos_gen")
    private Long id;
    private String designation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    private Province province;
}
