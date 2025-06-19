package com.Desafio.LiterAlura.Repositorio;

import com.Desafio.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILibroRepository extends JpaRepository<Libro,Long> {

    boolean existsByBookTitle(String bookTitle);

    List<Libro> findByBookTitleContainingIgnoreCase(String title);

    List<Libro> findByAutorAutorNameContainingIgnoreCase(String autorNombre);


}
