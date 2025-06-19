package com.Desafio.LiterAlura.Repositorio;

import com.Desafio.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IAutorRepository extends JpaRepository<Autor,Long> {

    @Query("SELECT a FROM Autor a WHERE a.autorName= :nombre")
    Optional<Autor> buscarAutorPorNombre(@Param("nombre") String nombre);

    @Query("SELECT a FROM Autor a " +
            "WHERE a.dateOfBirth <= :fechaFinal " +
            "AND (a.dateOfDeath IS NULL OR a.dateOfDeath >= :fechaInicio)")
    List<Autor> findAutoresVivosEnPeriodo(@Param("fechaInicio") LocalDate fechaInicio,
                                          @Param("fechaFinal") LocalDate fechaFinal);

}
