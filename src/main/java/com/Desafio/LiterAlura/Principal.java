package com.Desafio.LiterAlura;

import com.Desafio.LiterAlura.dto.AutorDTO;
import com.Desafio.LiterAlura.dto.LibroDTO;
import com.Desafio.LiterAlura.service.impl.AutorService;
import com.Desafio.LiterAlura.service.impl.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {
    Scanner teclado = new Scanner(System.in);

    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;


    public void menuPrincipal() {

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_RED = "\u001B[31m";

        int opcion = 0;
        LibroDTO libroDTO = null;

        do {

            System.out.println(ANSI_CYAN + "╔══════════════════════════════════╗" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "║          " + ANSI_BLUE + "📚 MENÚ PRINCIPAL 📚" + ANSI_CYAN + "         ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╠══════════════════════════════════╣" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "║ 1. Buscar Libro y Guardar         ║" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "║ 2. Listar Todos los Libros        ║" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "║ 3. Buscar Libro por Título        ║" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "║ 4. Buscar Libro por Autor         ║" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "║ 5. Buscar Libros por Idioma       ║" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "║ 6. Buscar Autores en Año          ║" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "║ 7. Listar Autores Registrados     ║" + ANSI_RESET);
            System.out.println(ANSI_GREEN +   "║ 8. Top 10 Mejores Descargas       ║" + ANSI_RESET);
            System.out.println(ANSI_GREEN +   "║ 9. Salir                         ║" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "╚══════════════════════════════════╝" + ANSI_RESET);
            System.out.print(ANSI_YELLOW + "👉 Ingrese la opción: " + ANSI_RESET);

            try {

                opcion = Integer.parseInt(teclado.nextLine());

                switch (opcion) {
                    case 1:
                        libroService.guardarLibro(teclado);
                        break;
                    case 2:
                        List<LibroDTO> libros = libroService.ListarLibros();
                        if (!libros.isEmpty()) {
                            libroService.mostrarLibros(libros);
                        }
                        break;
                    case 3:
                        System.out.println("👉 Ingrese el titulo del Libro que Deseas Buscar");
                        String titulo = teclado.nextLine();
                        List<LibroDTO> librosDTO = libroService.buscarLibrosPorTitulo(titulo);

                        if (librosDTO.isEmpty()) {
                            System.out.println("❌ No se encontraron libros con ese título.");
                        } else {
                            System.out.println("📚 Libros encontrados:");
                            for (LibroDTO libro : librosDTO) {
                                libroService.mostrarDatosLibro(libro);
                                System.out.println("----------------------------");
                            }
                        }

                        break;
                    case 4:
                        System.out.println("👉 Ingrese Autor del Libro que Deseas Buscar");
                        String autorNombre = teclado.nextLine();
                        librosDTO = libroService.buscarLibrosPorAutor(autorNombre);

                        if (librosDTO.isEmpty()) {
                            System.out.println("❌ No se encontraron libros de ese autor.");
                        } else {
                            System.out.println("📚 Libros encontrados:");
                            for (LibroDTO libro : librosDTO) {
                                libroService.mostrarDatosLibro(libro);
                                System.out.println("----------------------------");
                            }
                        }
                        break;
                    case 5:

                        System.out.print("🌍 Ingrese el código del idioma (ej: en, es, fr): ");
                        String idioma = teclado.nextLine().trim();

                        List<LibroDTO> librosPorIdioma = libroService.buscarLibrosPorIdioma(idioma);

                        if (librosPorIdioma.isEmpty()) {
                            System.out.println("📭 No hay libros registrados en el idioma '" + idioma + "'.");
                        } else {
                            libroService.mostrarLibros(librosPorIdioma);
                            System.out.println("\n📊 Total de libros en idioma '" + idioma + "': " + librosPorIdioma.size());

                        }
                        break;

                    case 6:
                        System.out.print("Ingrese el año para buscar autores vivos: ");
                        String anioStr = teclado.nextLine().trim();
                        try {
                            int anio = Integer.parseInt(anioStr);
                            List<AutorDTO> autoresVivos = autorService.listarAutoresVivosEnAnio(anio);

                            if (autoresVivos.isEmpty()) {
                                System.out.println("❌ No se encontraron autores vivos en el año " + anio);
                            } else {
                                System.out.println("📚 Autores vivos en " + anio + ":");
                                autoresVivos.forEach(a -> System.out.println("🖋️ " + a.name()));
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("⚠️ Año inválido. Ingrese un número válido.");
                        }
                        break;

                    case 7:
                        List<AutorDTO> autorDTOList = autorService.listarAutoresRegistrados();
                        if (!autorDTOList.isEmpty()) {
                            autorService.mostrarDatosAutores(autorDTOList);
                        }
                        break;

                    case 8:
                        libroService.mostrarTop10LibrosMasDescargados();
                        break;
                    case 9:
                        System.out.println("👋 Saliendo de la aplicación. ¡Hasta luego!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("❌ Opción inválida.");

                }

            } catch (NumberFormatException e) {
                System.out.println("❌ Ingrese un número válido.");
            }


        } while (opcion !=9);
    }
}

