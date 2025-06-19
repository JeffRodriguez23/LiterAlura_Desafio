package com.Desafio.LiterAlura.optionalMethod;

import com.Desafio.LiterAlura.dto.AutorDTO;
import com.Desafio.LiterAlura.dto.LibroDTO;
import com.Desafio.LiterAlura.model.Autor;
import com.Desafio.LiterAlura.model.Libro;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class LibroMapper {

    //Mapeo de libro a LibroDTO
    public LibroDTO libroToLibroDTO(Libro libro) {
        if (libro == null) return null;

        Autor autor = libro.getAutor();
        AutorDTO autorDTO = new AutorDTO(
                autor.getAutorName(),
                autor.getDateOfBirth() != null ? autor.getDateOfBirth().getYear() : null,
                autor.getDateOfDeath() != null ? autor.getDateOfDeath().getYear() : null
        );

        List<String> idiomas = List.of(libro.getBookLanguages().split(","));

        return new LibroDTO(
                libro.getBookTitle(),
                idiomas,
                libro.getBookDownloads(),
                List.of(autorDTO)
        );
    }

    //mapeo de LibroDTO a Libro
    public Libro libroDTOToLibro(LibroDTO libroDTOdto, Autor autor) {
        if (libroDTOdto == null || autor == null) return null;

        String idiomas = String.join(",", libroDTOdto.languages());

        return Libro.builder()
                .bookTitle(libroDTOdto.title())
                .bookLanguages(idiomas)
                .bookDownloads(libroDTOdto.downloadCount())
                .autor(autor)
                .build();
    }

    //mapeo de listaLibros a listaLibrosDTO
    public List<LibroDTO> librosListToLibrosDTOList(List<Libro> librosLista) {
        if (librosLista == null || librosLista.isEmpty()) {
            return List.of();
        }
        return librosLista.stream()
                .map(this::libroToLibroDTO)
                .toList();
    }

    //metodo para extraer el primer autor de la lista recibida por Gutendex
    public AutorDTO extraerPrimerAutor(LibroDTO libroDTO) {
        if (libroDTO.authors() != null && !libroDTO.authors().isEmpty()) {
            return libroDTO.authors().get(0);
        }
        return null;
    }

    //metodo para convertir AutorDTO a Autor
    public Autor convertirAutorDTOaEntidad(AutorDTO autorDTO) {
        if (autorDTO == null) return null;

        return Autor.builder()
                .autorName(autorDTO.name())
                .dateOfBirth(autorDTO.birthYear() != null ? LocalDate.of(autorDTO.birthYear(), 1, 1) : null)
                .dateOfDeath(autorDTO.deathYear() != null ? LocalDate.of(autorDTO.deathYear(), 1, 1) : null)
                .build();
    }

    //metodo para convertir Autor a AutorDTO
    public AutorDTO convertirAutorAAutorDTO(Autor autor) {
        if (autor == null) return null;

        return new AutorDTO(
                autor.getAutorName(),
                autor.getDateOfBirth() != null ? autor.getDateOfBirth().getYear() : null,
                autor.getDateOfDeath() != null ? autor.getDateOfDeath().getYear() : null
        );
    }


    //metodo para convertir lista Autores a Lista AutoresDTO
    public List<AutorDTO> autoresListToAutoresDTOList(List<Autor> autorList) {
        if (autorList == null || autorList.isEmpty()) {
            return List.of();
        }
        return autorList.stream()
                .map(this::convertirAutorAAutorDTO)
                .toList();
    }
}

