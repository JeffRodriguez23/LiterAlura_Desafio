package com.Desafio.LiterAlura.service;

import com.Desafio.LiterAlura.dto.AutorDTO;
import com.Desafio.LiterAlura.model.Autor;

import java.util.List;

public interface IAutorService {

    public void guardarAutor(Autor autor);
    public Autor buscarAutorPorNombre(String nombreAutor);
    public List<AutorDTO> listarAutoresVivosEnAnio(int anio);
    public List<AutorDTO> listarAutoresRegistrados();
    public void  mostrarDatosAutores(List<AutorDTO>autorDTOList);
}
