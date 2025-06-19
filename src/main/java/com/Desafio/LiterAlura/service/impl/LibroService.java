package com.Desafio.LiterAlura.service.impl;

import com.Desafio.LiterAlura.Repositorio.ILibroRepository;
import com.Desafio.LiterAlura.dto.AutorDTO;
import com.Desafio.LiterAlura.dto.GutendexRespuesta;
import com.Desafio.LiterAlura.dto.LibroDTO;
import com.Desafio.LiterAlura.model.Autor;
import com.Desafio.LiterAlura.model.Libro;
import com.Desafio.LiterAlura.optionalMethod.AnsiColors;
import com.Desafio.LiterAlura.optionalMethod.CreateHttpRequest;
import com.Desafio.LiterAlura.optionalMethod.LibroMapper;
import com.Desafio.LiterAlura.service.ILibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@Service
public class LibroService implements ILibroService {


    @Autowired
    private ILibroRepository libroRepository;
    @Autowired
    private AutorService autorService;
    @Autowired
    private CreateHttpRequest createHttpRequest;
    @Autowired
    private ConvertirDato convertirDatos;
    @Autowired
    private LibroMapper libroMapper;


    @Override
    public void guardarLibro(Scanner teclado) {

        System.out.print("📘 Escribe el título del libro a buscar: ");
        String titulo = teclado.nextLine();

        LibroDTO libroDTO = buscarLibroEnGutendex(titulo);

        if (libroDTO == null) {
            System.out.println("❌ No se recibió información del libro.");
            return;
        }

        System.out.println("desea Agregar El Libro a la Base de datos? ");
        String respuesta = teclado.nextLine();

        if (!respuesta.equalsIgnoreCase("si")) {
            System.out.println("⛔ Operación cancelada por el usuario.");
            return;
        }


        //validar la existencia de un autor
        if (libroDTO.authors().isEmpty()) {
            System.out.println("❌ El libro no tiene autores. No se puede guardar.");
            return;
        }
        //si hay mas de un autor , tomamos el primero
        AutorDTO autorDTO = libroMapper.extraerPrimerAutor(libroDTO);

        //buscamos si el autor ya existe en la BD
        Autor autor = autorService.buscarAutorPorNombre(autorDTO.name());

        //si no existe el autor, creamos y Guardamos el libro en la BD
        if (autor == null) {
            autor = libroMapper.convertirAutorDTOaEntidad(autorDTO);
            autorService.guardarAutor(autor);
            System.out.println("✅ Autor guardado: " + autor.getAutorName());
        }
        //buscamos si el libro existe
        if (existeLibro(libroDTO.title())) {
            System.out.println("⚠️ El libro ya existe en la base de datos.");
            return;
        }

        //guardamos  el Libro
        Libro libro = libroMapper.libroDTOToLibro(libroDTO, autor);
        libroRepository.save(libro);

        System.out.println("✅ Libro guardado exitosamente: " + libro.getBookTitle());

    }

    @Override
    public boolean existeLibro(String titulo) {
        return libroRepository.existsByBookTitle(titulo);
    }

    @Override
    public LibroDTO buscarLibroEnGutendex(String titulo) {

        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "+");

        String json = createHttpRequest.obtenerDatos(url);

        GutendexRespuesta response = convertirDatos.obtenerDatos(json, GutendexRespuesta.class);

        if (response.results().isEmpty()) {
            System.out.println("No se encontró ningún libro con ese título.");
            return null;
        }

        LibroDTO libroDTO = response.results().get(0);
        mostrarDatosLibro(libroDTO);
        return libroDTO;
    }

    @Override
    public void mostrarDatosLibro(LibroDTO libroDTO) {
        System.out.println(AnsiColors.BLUE + "\n╔══════════════════════ LIBRO ENCONTRADO ══════════════════════╗" + AnsiColors.RESET);
        System.out.println(AnsiColors.GREEN + "📖 Título      : " + AnsiColors.RESET + libroDTO.title());
        System.out.println(AnsiColors.GREEN + "🌐 Idiomas     : " + AnsiColors.RESET + String.join(", ", libroDTO.languages()));
        System.out.println(AnsiColors.GREEN + "⬇️ Descargas   : " + AnsiColors.RESET + libroDTO.downloadCount());

        if (!libroDTO.authors().isEmpty()) {
            AutorDTO autorDTO = libroDTO.authors().get(0);
            System.out.println(AnsiColors.CYAN + "\n--- Información del Autor Principal ---" + AnsiColors.RESET);
            System.out.println(AnsiColors.YELLOW + "✍️ Nombre        : " + AnsiColors.RESET + autorDTO.name());
            System.out.println(AnsiColors.YELLOW + "📅 Nacimiento    : " + AnsiColors.RESET + (autorDTO.birthYear() != null ? autorDTO.birthYear() : "Desconocido"));
            System.out.println(AnsiColors.YELLOW + "⚰️ Fallecimiento : " + AnsiColors.RESET + (autorDTO.deathYear() != null ? autorDTO.deathYear() : "Desconocido"));
        } else {
            System.out.println(AnsiColors.YELLOW + "❌ Autor        : " + AnsiColors.RESET + "No disponible");
        }

        System.out.println(AnsiColors.BLUE + "╚══════════════════════════════════════════════════════════════╝" + AnsiColors.RESET);
    }

    @Override
    public List<LibroDTO> ListarLibros() {
        List<Libro> listaLibros = libroRepository.findAll();
        if (listaLibros.isEmpty()) {
            System.out.println("📭 La lista de libros está vacía.");
            return List.of();
        }
        return libroMapper.librosListToLibrosDTOList(listaLibros);
    }

    @Override
    public List<LibroDTO> buscarLibrosPorTitulo(String titulo) {
        List<Libro> libros = libroRepository.findByBookTitleContainingIgnoreCase(titulo);
        return libros.stream()
                .map(libroMapper::libroToLibroDTO)
                .toList();
    }

    @Override
    public List<LibroDTO> buscarLibrosPorAutor(String nombreAutor) {
        List<Libro> libros = libroRepository.findByAutorAutorNameContainingIgnoreCase(nombreAutor);
        return libros.stream()
                .map(libroMapper::libroToLibroDTO)
                .toList();
    }


    @Override
    public void mostrarLibros(List<LibroDTO> librosDTO) {
        System.out.println(AnsiColors.BLUE + "\n╔══════════════════ LISTADO DE LIBROS ENCONTRADOS ══════════════════╗" + AnsiColors.RESET);

        for (int i = 0; i < librosDTO.size(); i++) {
            LibroDTO libroDTO = librosDTO.get(i);

            System.out.println(AnsiColors.CYAN + "\n🔹 Libro #" + (i + 1) + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "   📖 Título      : " + AnsiColors.RESET + libroDTO.title());
            System.out.println(AnsiColors.GREEN + "   🌐 Idiomas     : " + AnsiColors.RESET + String.join(", ", libroDTO.languages()));
            System.out.println(AnsiColors.GREEN + "   ⬇️ Descargas   : " + AnsiColors.RESET + libroDTO.downloadCount());

            if (!libroDTO.authors().isEmpty()) {
                AutorDTO autor = libroDTO.authors().get(0); // asumimos solo uno
                System.out.println(AnsiColors.YELLOW + "   ✍️ Autor       : " + AnsiColors.RESET + autor.name());

                String nacimiento = autor.birthYear() != null ? autor.birthYear().toString() : "Desconocido";
                String fallecimiento = autor.deathYear() != null ? autor.deathYear().toString() : "Desconocido";

                System.out.println(AnsiColors.YELLOW + "   📅 Nacimiento  : " + AnsiColors.RESET + nacimiento);
                System.out.println(AnsiColors.YELLOW + "   ⚰️ Fallecimiento: " + AnsiColors.RESET + fallecimiento);
            } else {
                System.out.println(AnsiColors.YELLOW + "   ❌ Sin autor registrado." + AnsiColors.RESET);
            }

            System.out.println(AnsiColors.BLUE + "---------------------------------------------------------------------" + AnsiColors.RESET);
        }

        System.out.println(AnsiColors.GREEN + "\n✅ Total de libros: " + librosDTO.size() + AnsiColors.RESET);
        System.out.println(AnsiColors.BLUE + "╚════════════════════════════════════════════════════════════════════╝" + AnsiColors.RESET);
    }

    @Override
    public List<LibroDTO> buscarLibrosPorIdioma(String idioma) {
        List<Libro> libros = libroRepository.findAll();

        return libros.stream()
                .filter(libro -> {
                    String[] idiomas = libro.getBookLanguages().split(",");
                    return Arrays.asList(idiomas).contains(idioma);
                })
                .map(libroMapper::libroToLibroDTO)
                .toList();

    }

    @Override
    public List<LibroDTO> obtenerTop10LibrosMasDescargados() {
        List<Libro> todosLibros = libroRepository.findAll();

        return todosLibros.stream()
                .sorted(Comparator.comparingInt(Libro::getBookDownloads).reversed())
                .limit(10)
                .map(libroMapper::libroToLibroDTO)
                .toList();
    }

    @Override
    public void mostrarTop10LibrosMasDescargados() {
        List<LibroDTO> top10Libros = obtenerTop10LibrosMasDescargados();

        if (top10Libros.isEmpty()) {
            System.out.println("❌ No hay libros para mostrar.");
            return;
        }

        System.out.println("\n🏆 TOP 10 LIBROS MÁS DESCARGADOS:\n");

        for (int i = 0; i < top10Libros.size(); i++) {
            System.out.println("📘 Puesto #" + (i + 1));
            mostrarDatosLibro(top10Libros.get(i));
            System.out.println("────────────────────────────────────────────\n");
        }
    }
}