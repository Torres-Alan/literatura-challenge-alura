package com.alurachallenge.literatura_challenge_alura.principal;

import com.alurachallenge.literatura_challenge_alura.model.Autor;
import com.alurachallenge.literatura_challenge_alura.model.DatosLibro;
import com.alurachallenge.literatura_challenge_alura.model.DatosRespuesta;
import com.alurachallenge.literatura_challenge_alura.model.Libro;
import com.alurachallenge.literatura_challenge_alura.repository.AutorRepository;
import com.alurachallenge.literatura_challenge_alura.repository.LibroRepository;
import com.alurachallenge.literatura_challenge_alura.service.ConsumoAPI;
import com.alurachallenge.literatura_challenge_alura.service.ConvierteDatos;

import java.util.List;
import java.util.Scanner;

public class Principal {

    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final String URL_BASE = "https://gutendex.com/books/?search=";

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("""
                    == MENÚ PRINCIPAL ==
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """);

            opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosEnAnio();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("== Aplicación finalizada ==\n");
                default -> System.out.println("== Opción inválida ==\n");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.print("- Escribe el título del libro: ");
        String tituloBuscado = teclado.nextLine().trim();

        var libroExistente = libroRepository.findByTituloIgnoreCase(tituloBuscado);
        if (libroExistente.isPresent()) {
            System.out.println("== El libro ya está registrado ==\n");
            return;
        }

        String json = consumoAPI.obtenerDatos(URL_BASE + tituloBuscado.replace(" ", "+"));
        var datosRespuesta = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (datosRespuesta != null && !datosRespuesta.resultados().isEmpty()) {
            DatosLibro datosLibro = datosRespuesta.resultados().get(0);

            Libro libro = new Libro();
            libro.setTitulo(datosLibro.titulo());
            libro.setNumeroDeDescargas(datosLibro.numeroDeDescargas());
            libro.setIdiomas(datosLibro.idioma());

            List<Autor> autores = datosLibro.autor().stream()
                    .map(a -> new Autor(a.name(), a.nacimiento(), a.fallecimiento()))
                    .toList();

            autores.forEach(a -> a.setLibro(libro));
            libro.setAutores(autores);
            libroRepository.save(libro);

            System.out.println("Libro encontrado y guardado:");
            System.out.println(libro);
        } else {
            System.out.println("No se encontró ningún libro con ese título.\n");
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("== No hay libros registrados ==");
            return;
        }
        System.out.println("== Lista de libros registrados ==");
        libros.forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        var autores = autorRepository.findAll();

        System.out.println("== Lista de autores registrados ==");
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.\n");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnAnio() {
        System.out.print("- Escribe el año a consultar: ");
        int anio = Integer.parseInt(teclado.nextLine());

        var autoresVivos = autorRepository
                .findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqualOrFallecimientoIsNull(anio, anio);

        if (autoresVivos.isEmpty()) {
            System.out.println("== No hay autores vivos en ese año ==\n");
        } else {
            System.out.println("== Autores vivos en el año " + anio + " ==");
            autoresVivos.forEach(System.out::println);
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
        - Ingresa el idioma para buscar libros:
        es - Español
        en - Inglés
        fr - Francés
        pt - Portugués
        """);

        System.out.print("Código de idioma: ");
        String codigo = teclado.nextLine().trim().toLowerCase();

        List<Libro> libros = libroRepository.findByIdiomasContainingIgnoreCase(codigo);

        if (libros.isEmpty()) {
            System.out.println("== No se encontraron libros en el idioma '" + codigo + "' ==\n");
        } else {
            System.out.println("== Libros en idioma '" + codigo + "' ==");
            libros.forEach(System.out::println);
        }
    }



}
