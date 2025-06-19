# 📚 Desafío LiterAlura

**Desafío LiterAlura** es una aplicación de consola desarrollada en **Java con Spring Boot** que permite interactuar con la API pública [Gutendex](https://gutendex.com/), la cual ofrece libros de dominio público. La aplicación permite buscar, guardar y consultar libros y autores, almacenando la información en una base de datos relacional PostgreSQL.

---

## 🎯 Funcionalidades principales

- 🔎 Buscar libros desde la API de Gutendex e importarlos a la base de datos.
- 📥 Guardar libros seleccionados junto a sus autores relacionados.
- 📚 Listar todos los libros almacenados.
- 👤 Listar todos los autores registrados.
- 🔤 Buscar libros por:
  - Título
  - Autor
  - Idioma
- ⭐ Obtener el **Top 10 libros más descargados** según la API.
- 📅 Consultar **autores vivos en un año específico** (utilizando derived queries en JPA).
- 📘 Interfaz mediante consola con menús interactivos y mensajes estilizados.

---

## ⚙️ Tecnologías utilizadas

| Herramienta       | Descripción                                 |
|-------------------|---------------------------------------------|
| **Java 17+**       | Lenguaje de programación principal          |
| **Spring Boot**   | Framework para desarrollo de aplicaciones   |
| **Lombok**        | Generación automática de getters, setters, etc. |
| **Jackson Databind** | Deserialización de JSON a objetos Java  |
| **Records**       | Representación inmutable de datos           |
| **PostgreSQL**    | Base de datos relacional                    |
| **JPA/Hibernate** | Mapeo objeto-relacional (ORM)               |

---

## 🗂️ Organización del proyecto

```
Desafio_LiterAlura
│
├── DTO/
│   ├── AutorDTO.java
│   ├── LibroDTO.java
│   └── GutendexRespuesta.java
│
├── Model/
│   ├── Autor.java
│   └── Libro.java
│
├── OptionalMethod/
│   ├── AnsiColor.java
│   ├── CreateHttpRequest.java
│   └── LibroMapper.java
│
├── Repositorio/
│   ├── IAutorRepository.java
│   └── ILibroRepository.java
│
├── Service/
│   ├── interfaces/
│   │   ├── IAutorService.java
│   │   ├── ILibroService.java
│   │   └── IConvertirDato.java
│   └── impl/
│       ├── AutorService.java
│       ├── LibroService.java
│       └── ConvertirDato.java
│
├── Principal/
│   └── Principal.java
│
└── LiterAluraApplication.java
```

---

## 🧠 Arquitectura y flujo de ejecución

El diseño de la aplicación sigue el principio de separación de responsabilidades. Se utiliza inyección de dependencias con `@Autowired` para acceder a los servicios desde la clase principal.

```plaintext
Usuario CLI
   ↓
Principal.java (Menú - Scanner)
   ↓
Servicios (LibroService / AutorService)
   ↓
Repositorios JPA (ILibroRepository / IAutorRepository)
   ↓
Base de datos PostgreSQL
```

---

## 📝 Notas técnicas

- Las peticiones HTTP se realizan mediante una clase personalizada `CreateHttpRequest`.
- Los datos provenientes de la API Gutendex se deserializan con Jackson y se convierten mediante `LibroMapper`.
- Los datos se almacenan utilizando `JpaRepository` y la relación entre `Libro` y `Autor` se maneja adecuadamente.
- El uso de `Record` para los DTO permite inmutabilidad y claridad en la transferencia de datos.
- El menú principal está encapsulado en una clase `Principal` ejecutada mediante `CommandLineRunner` desde `LiterAluraApplication`.

---

## 👨‍💻 Autor

**Jhonny Jefferson Rodríguez Pérez**  
🔗 [www.linkedin.com/in/jhonny-jefferson-rodriguez-perez-4a022a34b](https://www.linkedin.com/in/jhonny-jefferson-rodriguez-perez-4a022a34b)
