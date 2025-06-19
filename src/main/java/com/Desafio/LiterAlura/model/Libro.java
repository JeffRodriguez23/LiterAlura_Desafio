package com.Desafio.LiterAlura.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "Libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    @Column(name = "titulo_libro",unique = true)
    private String bookTitle;
    @Column(name = "idiomas")
    private String bookLanguages;
    @Column(name = "numero_descargas")
    private int bookDownloads;

    @ManyToOne(targetEntity = Autor.class)
    @JoinColumn(name="autor_id")
    private Autor autor;

}
