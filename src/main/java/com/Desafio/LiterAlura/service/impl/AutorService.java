package com.Desafio.LiterAlura.service.impl;

import com.Desafio.LiterAlura.Repositorio.IAutorRepository;
import com.Desafio.LiterAlura.dto.AutorDTO;
import com.Desafio.LiterAlura.model.Autor;
import com.Desafio.LiterAlura.optionalMethod.AnsiColors;
import com.Desafio.LiterAlura.optionalMethod.LibroMapper;
import com.Desafio.LiterAlura.service.IAutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
@Service
public class AutorService implements IAutorService {

    @Autowired
    private IAutorRepository autorRepository;
    @Autowired
    private LibroMapper libroMapper;

    @Override
    public void guardarAutor(Autor autor) {
        autorRepository.save(autor);
    }


    @Override
    public Autor buscarAutorPorNombre(String nombreAutor) {
        Optional<Autor>autorOptional=autorRepository.buscarAutorPorNombre(nombreAutor);
        if (autorOptional.isPresent()){
            Autor autor=autorOptional.get();
            return autor;
        }
        return null;
    }

    @Override
    public List<AutorDTO> listarAutoresVivosEnAnio(int anio) {
        LocalDate fechaInicio = LocalDate.of(anio, 1, 1);
        LocalDate fechaFinal = LocalDate.of(anio, 12, 31);

        List<Autor> autores = autorRepository.findAutoresVivosEnPeriodo(fechaInicio, fechaFinal);

        return autores.stream()
                .map(libroMapper::convertirAutorAAutorDTO)
                .toList();
    }

    @Override
    public List<AutorDTO> listarAutoresRegistrados() {
        List<Autor>autorList=autorRepository.findAll();
        if (autorList.isEmpty()){
            System.out.println("📭 La lista de Autores está vacía.");
            return List.of();
        }
       return libroMapper.autoresListToAutoresDTOList(autorList);
    }

    @Override
    public void mostrarDatosAutores(List<AutorDTO> autorDTOList) {
        if (autorDTOList == null || autorDTOList.isEmpty()) {
            System.out.println(AnsiColors.RED + "\n❌ No hay autores para mostrar." + AnsiColors.RESET);
            return;
        }

        System.out.println(AnsiColors.BLUE + "\n╔══════════════════════ LISTADO DE AUTORES ══════════════════════╗" + AnsiColors.RESET);

        List<AutorDTO> autoresOrdenados = autorDTOList.stream()
                .sorted(Comparator.comparing(a -> a.name().toLowerCase()))
                .toList();

        for (int i = 0; i < autoresOrdenados.size(); i++) {
            AutorDTO autor = autoresOrdenados.get(i);

            System.out.println(AnsiColors.CYAN + "\n🔹 Autor #" + (i + 1) + AnsiColors.RESET);
            System.out.println(AnsiColors.YELLOW + "   ✍️ Nombre            : " + AnsiColors.RESET + autor.name());

            String nacimiento = autor.birthYear() != null ? autor.birthYear().toString() : "Desconocido";
            String fallecimiento = autor.deathYear() != null ? autor.deathYear().toString() : "Desconocido";

            System.out.println(AnsiColors.YELLOW + "   📅 Año nacimiento    : " + AnsiColors.RESET + nacimiento);
            System.out.println(AnsiColors.YELLOW + "   ⚰️ Año fallecimiento : " + AnsiColors.RESET + fallecimiento);

            System.out.println(AnsiColors.BLUE + "---------------------------------------------------------------------" + AnsiColors.RESET);
        }

        System.out.println(AnsiColors.GREEN + "\n✅ Total de autores: " + autoresOrdenados.size() + AnsiColors.RESET);
        System.out.println(AnsiColors.BLUE + "╚════════════════════════════════════════════════════════════════════╝" + AnsiColors.RESET);
    }


}
