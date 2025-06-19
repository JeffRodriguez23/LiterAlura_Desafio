package com.Desafio.LiterAlura.service;

import com.Desafio.LiterAlura.dto.LibroDTO;
import com.Desafio.LiterAlura.model.Libro;

import java.util.List;
import java.util.Scanner;


public interface ILibroService {

    public void guardarLibro(Scanner teclado);

    public boolean existeLibro(String titulo);

    public LibroDTO buscarLibroEnGutendex(String titulo);

    public void mostrarDatosLibro(LibroDTO libroDTO);

    public List<LibroDTO> ListarLibros();

    public List<LibroDTO> buscarLibrosPorTitulo(String titulo);

    public List<LibroDTO> buscarLibrosPorAutor(String nombreAutor);

    public void mostrarLibros(List<LibroDTO> librosDTO);

    public List<LibroDTO>buscarLibrosPorIdioma(String idioma);

    public List<LibroDTO> obtenerTop10LibrosMasDescargados();

    public void mostrarTop10LibrosMasDescargados();
}
