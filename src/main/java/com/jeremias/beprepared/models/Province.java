package com.jeremias.beprepared.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(sequenceName = "province_seq", name = "province_gen", allocationSize = 1, initialValue = 1)
@Table(name = "provincias")
@Entity
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "province_gen")
    private Long id;
    private String designation;

    @OneToMany(mappedBy = "province", fetch = FetchType.LAZY)
    private List<City> cities;
}
