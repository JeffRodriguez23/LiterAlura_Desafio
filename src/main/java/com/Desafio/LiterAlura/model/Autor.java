package com.Desafio.LiterAlura.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "Autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long autorId;
    @Column(name = "Autor_nombre" , unique = true)
    private String autorName;
    @Column(name = "fecha_nacimiento")
    private LocalDate dateOfBirth;
    @Column(name = "fecha_muerte")
    private LocalDate dateOfDeath;

    @OneToMany(targetEntity = Libro.class, mappedBy= "autor",orphanRemoval = true , fetch = FetchType.LAZY)
    private List<Libro>libros;
}
